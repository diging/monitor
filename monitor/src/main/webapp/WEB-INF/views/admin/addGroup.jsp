<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h3>Add a new group</h3>
<c:url value="/admin/groups/add" var="url" />
<form:form method="POST" action="${url}" modelAttribute="groupForm">
	<div class="form-group">
		<form:label path="name">Group Name:</form:label>
		<form:input type="text" class="form-control" path="name" />
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
				<input type="checkbox" name="appIds" value="${app.id}" /> <label>${app.name}</label>
			</p>
		</c:forEach>
	</div>

	<input class="btn btn-primary" type="submit"
		value="Add new Group" />
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