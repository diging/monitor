<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="time"%>

<h3>This is a list of all people being notified:</h3>
<a href="<c:url value="/admin/recipients/add"/>" class="btn btn-primary">Add
	Recipient</a>
<table class="table">
	<thead>
		<th scope="col">Name</th>
		<th scope="col">Email</th>
		<th></th>
	</thead>
	<c:forEach items="${recipients}" var="recipient">
		<tr>
			<td><c:out value="${recipient.name}" /></td>
			<td><c:out value="${recipient.email}" /></td>
			<td align="right"><sec:authorize access="hasAnyRole('ADMIN')">
					<div class="pull-right">
						<c:url value="/admin/recipients/${recipient.email}/delete" var="deleteUrl" />
						<form action="${deleteUrl}" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button title="Delete Recipient" type="submit" class="btn-link">
								<i class="fa fa-trash" aria-hidden="true"></i>
							</button>
						</form>
					</div>
				</sec:authorize></td>
		</tr>
	</c:forEach>
</table>
