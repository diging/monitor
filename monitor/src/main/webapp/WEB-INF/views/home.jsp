<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="time"%>
<c:url value="/reload"  var ="reloadUrl"/>
<script>
	window.setInterval(function() {
		ajaxCall()
	}, 60000);
	
	window.onload = function() {
		ajaxCall()
	};
	
	function ajaxCall(){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "${reloadUrl}",
			success : function(data) {
				$('#ajax_reload_alert').hide()
				data.forEach(update)
			},
			error : function(xhr, status, error) {
				console.error(xhr.responseText)
				console.error(status)
				console.error(error.message)
				$('#ajax_reload_alert').show()
			}
		});
	}
	
	function update(data) {
		$('#name_' + data.id).text(data.name)
		$('#desc_' + data.id).text(data.description)
		$('#url_' + data.id).text(data.healthUrl)
		$('#status_' + data.id).text(
				"App status is: " + data.lastAppTest.status)
		$('#status_mod_' + data.id).text(data.lastAppTest.status)		
		$('#time_' + data.id).text(parseDate(data.lastAppTest.pingTime))
		$('#time_mod_'+ data.id).text($('#time_' + data.id).text())
		
	}
	
	function parseDate(jsonDate) {
		return moment.parseZone(
				[ jsonDate.year, (jsonDate.monthValue),
						jsonDate.dayOfMonth, jsonDate.hour, jsonDate.minute,
						jsonDate.offset.id ], 'YYYY MM DD HH mm ZZ').local()
				.format('MMMM D, YYYY h:mm A')
	}
</script>
<h3>The following apps are being monitored:</h3>
<sec:authorize access="isAuthenticated()">
	<p>You are logged in.</p>
</sec:authorize>
<div class="alert alert-warning alert-dismissible" role="alert" id="ajax_reload_alert" style="display:none">
  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
  </button>
  <strong>Connection to server lost. Trying to connect...</strong>
</div>
<c:forEach items="${apps}" var="app">
	<sec:authorize access="hasAnyRole('ADMIN')">
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/delete" var="deleteUrl" />
			<form action="${deleteUrl}" method="POST">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button title="Delete App" type="submit" class="btn-link">
					<i style="padding: 10px; padding-left: 0px;" class="fa fa-trash"
						aria-hidden="true"></i>
				</button>
			</form>
		</div>
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/modify" var="modifyUrl" />
			<a href="${modifyUrl}"> 
				<i style="padding-top:15px;padding-right:8px;padding-left:1px;" title ="Modify App" class="fa fa-edit" aria-hidden="true"></i>
			</a>
		</div>
		<div class="pull-right">
				<c:url value="/admin/apps/${app.id}/modifyAuth" var="modifyAuth" />
				<a href="${modifyAuth}">
					<i style="padding-top:13px;padding-right:15px;padding-left:0px;" title ="Modify App authentication" class="fa fa-pencil"></i>
				</a>
			</div>
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/ping" var="pingUrl" />
			<form action="${pingUrl}" method="POST">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button title="Ping App" type="submit" class="btn-link">
					<i style="padding: 10px; padding-right: 10px; padding-left: 1px;"
						class="fa fa-bullseye" aria-hidden="true"></i>
				</button>
			</form>
		</div>
		<div class="pull-right">
			<button title="Details" type="submit" class="btn-link"
				data-toggle="modal" data-target="#modal_${app.id }"
				data-backdrop="false">
				<i style="padding: 10px; padding-right: 0px; padding-left: 0px;"
					class="fa fa-info-circle" aria-hidden="true"></i>
			</button>
		</div>
		<c:choose>
			<c:when test="${app.lastAppTest.status == 'ERROR'}">
				<div class="pull-right">
					<button title="Error Logs" type="submit" class="btn-link"
						data-toggle="modal" data-target="#modal_error_${app.id}"
						data-backdrop="false">
						<i style="padding: 10px; padding-right: 0px; padding-left: 0px;"
							class="fa fa-file-text-o" aria-hidden="true"></i>
					</button>
				</div>
			</c:when>
		</c:choose>
	</sec:authorize>
	<c:choose>
		<c:when test="${app.lastAppTest.status == 'OK'}">
			<div class="alert alert-success" role="alert">
				<i class="fa fa-check-circle" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'ERROR'}">
			<div class="alert alert-danger" role="alert">
				<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'IN_PROGRESS'}">
			<div class="alert alert-warning" role="alert">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'WARNING'}">
			<div class="alert alert-warning" role="alert">
				<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info" role="alert">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
		</c:otherwise>
	</c:choose>
	<strong id="name_${app.id}">${app.name}</strong> (<span
		id="url_${app.id}">${app.healthUrl}</span>)
			<p>
		<i id="desc_${app.id}">${app.description}</i>
	</p>
	<p>
		Last check was run on: <span id="time_${app.id }"><time:format
				value="${app.lastAppTest.pingTime}" pattern="MMM d, yyyy h:mm a" /></span>
		<br> <span id="status_${app.id}">App status is:
			${app.lastAppTest.status}</span>
	</p>
	<sec:authorize access="hasAnyRole('ADMIN')">
		<p>
			Recipients:
			<c:choose>
				<c:when test="${empty app.recipients || app.recipients.size() == recipientCount}">
						All
				</c:when>
				<c:otherwise>
					<c:forEach items="${app.recipients}" var="recipient"
						varStatus="loop">
	    					${recipient}<c:if test="${!loop.last}">,</c:if>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</p>
	</sec:authorize>
	</div>
	<div id="modal_error_${app.id}" class="modal fade;overflow:hidden"
		role="dialog" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p>
						App status : <b>${app.lastAppTest.status}</b>
					</p>
					<c:forEach items="${app.lastAppTest.pingResults}" var="pingResult">
						<p> Error code: ${pingResult.returnCode} </p>
						<p> Return Value: ${pingResult.returnValue} </p>
					</c:forEach>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-alert" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div id="modal_${app.id}" class="modal fade;overflow:hidden"
		role="dialog" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p>
						App Name: <b>${app.name}</b>
					</p>
					<p>
						Health Url: <b>${app.healthUrl}</b>
					</p>
					<p>
						Description: <b>${app.description}</b>
					</p>
					<p>
						App Status is: <b id="status_mod_${app.id}">${app.lastAppTest.status}</b>
					</p>
					<p>
						Last check was run on: <b id="time_mod_${app.id}"><time:format
								value="${app.lastAppTest.pingTime}" style="MS" /></b>
					</p>
					<p>
						Expected Return codes: <b>${app.expectedReturnCodes }</b>
					</p>
					<p>
						Warning Return codes: <b>${app.warningReturnCodes }</b>
					</p>
					<p>
						Timeout: <b>${app.timeout }</b>
					</p>
					<p>
						Retries: <b>${app.retries}</b>
					</p>
					<p>
						Ping Interval is: <b>${app.pingInterval}</b>
					</p>
					<p>
						Request Method is: <b>${app.method }</b>
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-alert" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</c:forEach>
<c:forEach items="${appGroups}" var= "group">
	<c:if test="${not empty group.apps}">
	<h5><b>${group.name}</b></h5>
	<c:forEach items="${group.apps}" var="app">
		<sec:authorize access="hasAnyRole('ADMIN')">
			<div class="pull-right">
				<c:url value="/admin/apps/${app.id}/delete" var="deleteUrl" />
				<form action="${deleteUrl}" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<button title="Delete App" type="submit" class="btn-link">
						<i style="padding:10px;padding-left:0px;" class="fa fa-trash" aria-hidden="true"></i>
					</button>
				</form>
			</div>
			<div class="pull-right">
				<c:url value="/admin/apps/${app.id}/modify" var="modifyUrl" />
				<a href="${modifyUrl}">
					<i style="padding-top:15px;padding-right:8px;padding-left:1px;" title ="Modify App" class="fa fa-edit" aria-hidden="true"></i>
				</a>
			</div>
			<div class="pull-right">
				<c:url value="/admin/apps/${app.id}/modifyAuth" var="modifyAuth" />
				<a href="${modifyAuth}">
					<i style="padding-top:13px;padding-right:15px;padding-left:0px;" title ="Modify App authentication" class="fa fa-pencil"></i>
				</a>
			</div>
			<div class="pull-right">
				<c:url value="/admin/apps/${app.id}/ping" var="pingUrl"/>
				<form action="${pingUrl}" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<button title="Ping App" type="submit" class="btn-link">
						<i style="padding:10px;padding-right:10px;padding-left:1px;" class="fa fa-bullseye" aria-hidden="true"></i>
					</button>
				</form>
			</div>
			<div class="pull-right">
					<button title="Details" type="submit" class="btn-link" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
						<i style="padding:10px;padding-right:0px;padding-left:0px;" class="fa fa-info-circle" aria-hidden="true"></i>
					</button>
			</div>
		</sec:authorize>
		<c:choose>
			<c:when test="${app.lastAppTest.status == 'OK'}">
				<div class="alert alert-success" role="alert">
					<i class="fa fa-check-circle" aria-hidden="true"></i>
			</c:when>
			<c:when test="${app.lastAppTest.status == 'ERROR'}">
				<div class="alert alert-danger" role="alert">
					<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
			</c:when>
			<c:when test="${app.lastAppTest.status == 'IN_PROGRESS'}">
				<div class="alert alert-warning" role="alert">
					<i class="fa fa-clock-o" aria-hidden="true"></i>
			</c:when>
			<c:when test="${app.lastAppTest.status == 'WARNING'}">
				<div class="alert alert-warning" role="alert">
					<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info" role="alert">
					<i class="fa fa-clock-o" aria-hidden="true"></i>
			</c:otherwise>
		</c:choose>
		<strong id="name_${app.id}">${app.name}</strong> (<span id="url_${app.id}">${app.healthUrl}</span>)
			<p>
			<i id="desc_${app.id}">${app.description}</i>
		</p>
		<p>
			Last check was run on:
			<span id="time_${app.id }"><time:format value="${app.lastAppTest.pingTime}" pattern ="MMM d, yyyy h:mm a" /></span>
			<br> <span id="status_${app.id}">App status is: ${app.lastAppTest.status}</span>
		</p>
		<sec:authorize access="hasAnyRole('ADMIN')">
			<p>
				Recipients:
				<c:choose>
					<c:when test="${empty app.recipients || app.recipients.size() == recipientCount}">
						All
				</c:when>
					<c:otherwise>
						<c:forEach items="${app.recipients}" var="recipient" varStatus="loop">
	    					${recipient}<c:if test="${!loop.last}">,</c:if>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</p>
		</sec:authorize>
		</div>
		<div id="modal_${app.id}" class="modal fade;overflow:hidden" role="dialog" aria-hidden="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<p>App Name: <b>${app.name}</b></p>
						<p>Health Url: <b>${app.healthUrl}</b></p>
						<p>Description: <b>${app.description}</b></p>
						<p>App Status is: <b id="status_mod_${app.id}">${app.lastAppTest.status}</b></p>
						<p>
							Last check was run on:
							<b id="time_mod_${app.id}"><time:format value="${app.lastAppTest.pingTime}" style="MS" /></b>
						</p>
						<p>Expected Return codes: <b>${app.expectedReturnCodes }</b></p>
						<p>Warning Return codes: <b>${app.warningReturnCodes }</b></p>
						<p>Timeout: <b>${app.timeout }</b></p>
						<p>Retries: <b>${app.retries}</b></p>
						<p>Ping Interval is: <b>${app.pingInterval}</b></p>
						<p>Request Method is: <b>${app.method }</b></p>
					</div>
					<div class="modal-footer">
	        			<button type="button" class="btn btn-alert" data-dismiss="modal">Close</button>
	      			</div>
				</div>
			</div>
		</div>
	</c:forEach>
	</c:if>
</c:forEach>