<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h3>Update Authentication information for this App</h3>
<c:url value="/admin/apps/${appForm.id}/modifyAuth" var="url" />
<form:form method="POST" action="${url}" modelAttribute="appForm">

	<div class="form-group">
		<form:label path="username">Username:</form:label>
		<form:input type="text" class="form-control" path="username" />
		<form:errors path="username" cssClass="error" />
	</div>
	<div class="form-group">
		<form:label path="password">Password:</form:label>
		<form:input type="password" class="form-control" path="password" />
		<form:errors path="password" cssClass="error" />
		<form:input type="hidden" class="form-control" path="updateUserInfo"
			value="true" />
	</div>
	<input class="btn btn-primary" type="submit" value="Update Credentials" />
	<a href="<c:url value="/" />" ><input class="btn btn-default" type="button" value="Cancel" /></a>
</form:form>
<br>