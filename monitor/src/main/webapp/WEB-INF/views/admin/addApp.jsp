<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:choose>
	<c:when test="${empty appForm.id}">
		<c:url value="/admin/apps/add" var="url"/>
	</c:when>
	<c:otherwise>
		<c:url value="/admin/apps/${appForm.id}/modify" var="url"/>
	</c:otherwise>
</c:choose>
<form:form method="POST" action="${url}" modelAttribute="appForm">
  <div class="form-group">
 	 <form:label path="name">App Name:</form:label>
    	 <form:input type="text" class="form-control" path="name" />
  </div>
  
  <div class="form-group">
 	 <form:label path="healthUrl">Health URL:</form:label>
    	 <form:input type="text" class="form-control" path="healthUrl" />
  </div>
  
  <div class="form-group">
 	 <form:label path="description">Description:</form:label>
    	 <form:input type="text" class="form-control" path="description" />
  </div>
  
  <div class="form-group">
 	 <form:label path="expectedReturnCodes">Expected return codes (comma-separated):</form:label>
    	 <form:input type="text" class="form-control" path="expectedReturnCodes" />
  </div>
  
  <div class="form-group">
 	 <form:label path="warningReturnCodes">Warning return codes (comma-separated):</form:label>
    	 <form:input type="text" class="form-control" path="warningReturnCodes" />
  </div>
  
  <div class="form-group">
  	 <form:label path="timeout">Method for ping:</form:label>
  	 <form:select class="form-control" path="method">
     	<form:option value="HEAD" label="HEAD" />
     	<form:option value="GET" label="GET" />
   	 </form:select>
  </div>
  
  <div class="form-group">
 	 <form:label path="timeout">Timeout (in ms):</form:label>
    	 <form:input type="number" class="form-control" path="timeout" value="30000" />
    	 <small>Default is 30 seconds.</small>
  </div>
  
  <div class="form-group">
 	 <form:label path="retries">Retries:</form:label>
    	 <form:input type="number" class="form-control" path="retries" />
  </div>
  
  <div class="form-group">
 	 <form:label path="pingInterval">Test interval (in seconds):</form:label>
    	 <form:input type="number" class="form-control" path="pingInterval" value="900" />
    	 <small>Minimum interval is 1 minute (60 seconds).</small>
  </div>
  
  <div class="form-group">
  <form:radiobutton id="groupCreate" name="group" value="NEW" path ="groupType"/>
 	 <form:label path="groupId">Create a new Group:</form:label>
    	 <form:input type="text" class="form-control" path="groupId"/>
  </div>
  		<p>Or</p> 
	<div class="form-group">
		<form:radiobutton id="groupSelect" name="group" value="EXISTING"
			path="groupType" />
		<form:label path="existingGroupId">Select from an Existing Group:</form:label>
		<form:select class="form-control" path="existingGroupId">
			<form:options items="${appForm.groupIds}" var="groups" itemValue="key" itemLabel="value" />
		</form:select>
	</div>

	<div class="form-group">
		<form:label path="recipientIds">Recipients:</form:label>
		<p>
		<i>
			<input type="checkbox" name="select-all" id="select-all" /> <label>Select/deselect
				all</label>
		</i>
		</p>		
		<c:forEach items="${appForm.recipients}" var="recipient">
			<p>
				<input type="checkbox" name="recipientIds" value="${recipient.email}"
				 <c:if test="${appRecipients.contains(recipient.email)}">checked="checked"</c:if>
				/> <label>${recipient.name}</label>
			</p>
		</c:forEach>
  </div>
  <c:choose>
  	<c:when test="${empty appForm.id}">
  		<input class="btn btn-primary" type="submit" value="Add App"/>
  	</c:when>
  	<c:otherwise>
  		<input class="btn btn-primary" type="submit" value="Modify App"/>
  	</c:otherwise>
  </c:choose>
</form:form>
<script>
	$('#select-all').click(function(event) {
		if (this.checked) {
			$(':checkbox').each(function() {
				this.checked = true;
			});
		} else {
			$(':checkbox').each(function() {
				this.checked = false;
			});
		}
	});
</script>