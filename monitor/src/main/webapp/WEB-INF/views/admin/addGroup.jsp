<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:choose>
	<c:when test="${empty groupForm.id}">
		<h3>Add a new group</h3>
		<c:url value="/admin/groups/add" var="url"/>
	</c:when>
	<c:otherwise>
		<h3>Modify group</h3>
		<c:url value="/admin/groups/${groupForm.id}/modify" var="url"/>
	</c:otherwise>
</c:choose>
<form:form method="POST" action="${url}" modelAttribute="groupForm">
	<div class="form-group">
		<form:label path="name">Group Name:</form:label>
		<form:input type="text" class="form-control" path="name" />
		<form:errors path="name" cssClass="error" />
		
	</div>
	
	<div class="form-group">
		<form:label path="appIds">Select apps in this group:</form:label>
		<p>
		<i>
			<input type="checkbox" name="select-all" id="select-all" /> <label>Select/deselect
				all</label>
		</i>
		</p>		
		<c:forEach items="${groupForm.apps}" var="app">
			<p>
				<input type="checkbox" name="appIds" value="${app.id}" 
				<c:if test="${appIds.contains(app.id)}">checked="checked"</c:if>
				/> <label>${app.name}</label>
			</p>
		</c:forEach>
	</div>
	
	<c:choose>
  	<c:when test="${empty groupForm.id}">
  		<input class="btn btn-primary" type="submit" value="Add new  Group"/>
  	</c:when>
  	<c:otherwise>
  		<input class="btn btn-primary" type="submit" value="Modify Group"/>
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