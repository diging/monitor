<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="time"%>
<h3>The following apps are being monitored:</h3>
<sec:authorize access="isAuthenticated()">
	<p>You are logged in.</p>
</sec:authorize>

<c:forEach items="${apps}" var="app">
	<sec:authorize access="hasAnyRole('ADMIN')">
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/delete" var="deleteUrl" />
			<form action="${deleteUrl}" method="POST">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button title="Delete App" type="submit" class="btn-link">
					<i style="padding: 10px;padding-left:0px;" class="fa fa-trash" aria-hidden="true"></i>
				</button>
			</form>
		</div>
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/modify" var="modifyUrl" />
			<form action="${modifyUrl}" method="GET">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button title="Modify App" type="submit" class="btn-link">
					<i style="padding: 11px;padding-right:0px;padding-left:0px;" class="fa fa-edit" aria-hidden="true"></i>
				</button>
			</form>
		</div>
		<div class="pull-right">
			<c:url value="/admin/apps/${app.id}/ping" var="pingUrl"/>
			<form action="${pingUrl}" method="POST">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button title="Ping App" type="submit" class="btn-link">
					<i style="padding: 10px;padding-right:0px;" class="fa fa-bullseye" aria-hidden="true"></i>
				</button>
			</form>
		</div>
	</sec:authorize>
	<c:choose>
		<c:when test="${app.lastAppTest.status == 'OK'}">
			<div class="alert alert-success" role="alert" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
				<i class="fa fa-check-circle" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'ERROR'}">
			<div class="alert alert-danger" role="alert" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
				<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'IN_PROGRESS'}">
			<div class="alert alert-warning" role="alert" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
		</c:when>
		<c:when test="${app.lastAppTest.status == 'WARNING'}">
			<div class="alert alert-warning" role="alert" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
				<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info" role="alert" data-toggle="modal" data-target="#modal_${app.id }" data-backdrop="false">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
		</c:otherwise>
	</c:choose>
	<strong>${app.name}</strong> (${app.healthUrl})
		<p>
		<i>${app.description}</i>
	</p>
	<p>
		Last check was run on:
		<time:format value="${app.lastAppTest.pingTime}" style="MS" />
		<br> App status is: ${app.lastAppTest.status}
	</p>
	<div id="modal_${app.id }" class="modal fade;overflow:hidden" role="dialog" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<p>App Name: ${app.name }</p>
					<p>Health Url: ${app.healthUrl }</p>
					<p>Description: ${app.description }</p>
					<p>App Status is: ${app.lastAppTest.status }</p>
					<p>
						Last check was run on:
						<time:format value="${app.lastAppTest.pingTime}" style="MS" />
					</p>
					<p>Expected Return codes: ${app.expectedReturnCodes }</p>
					<p>Warning Return codes: ${app.warningReturnCodes }</p>
					<p>Timeout: ${app.timeout }</p>
					<p>Retries: ${app.retries }</p>
					<p>Ping Interval is: ${app.pingInterval }</p>
					<p>Request Method is: ${app.method }</p>
				</div>
			</div>
		</div>
	</div>
	</div>
</c:forEach>