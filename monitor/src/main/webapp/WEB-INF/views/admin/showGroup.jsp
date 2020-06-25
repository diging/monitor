<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="time"%>

<h3>This is a list of all people being notified:</h3>
<a href="<c:url value="/admin/groups/add"/>" class="btn btn-primary">Add
	Group</a>
<table class="table">
	<thead>
		<th scope="col">Group Name</th>
		<th scope="col">Apps</th>
		<th></th>
	</thead>
	<c:forEach items="${groups}" var="group">
		<tr>
			<td><c:out value="${group.name}" /></td>
			<td>
			<c:choose>
			<c:when test="${empty group.apps}">
				None
			</c:when>
			<c:when test ="${group.apps.size() == appCount}">
				All
			</c:when>
			<c:otherwise>
			<c:forEach items="${group.apps}" var="app" varStatus="loop">
    				${app}<c:if test="${!loop.last}">,</c:if>
			</c:forEach>
			</c:otherwise>
			</c:choose>
			</td>
			<td align="right">
				<sec:authorize access="hasAnyRole('ADMIN')">
					<div class="pull-right">
						<c:url value="/admin/groups/${group.id}/delete" var="deleteUrl" />
						<form action="${deleteUrl}" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button title="Delete Group" type="submit" class="btn-link">
								<i class="fa fa-trash" aria-hidden="true"></i>
							</button>
						</form>
					</div>
					<div class="pull-right">
						<c:url value="/admin/groups/${group.id}/modify" var="modifyUrl" />
						<a href="${modifyUrl}">
							<i class="fa fa-edit" style="padding-top:7px;padding-right:10px" title="Modify Group" aria-hidden="true"></i>
						</a>
					</div>
				</sec:authorize>
			</td>
		</tr>
	</c:forEach>
</table>