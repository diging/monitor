<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url value="/admin/register/add" var="url" />
<form:form method="POST" action="${url}" modelAttribute="userForm" enctype="utf8">
	<div class="form-group">
		<form:label path="username">User Name:</form:label>
		<form:input type="text" class="form-control" path="username" />
	</div>

	<div class="form-group">
		<form:label path="password">Password:</form:label>
		<form:input type="password" class="form-control" path="password" />
	</div>
	<input class="btn btn-primary" type="submit" value="Add User" />
</form:form>