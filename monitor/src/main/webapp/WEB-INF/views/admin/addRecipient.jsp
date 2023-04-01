<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:choose>
	<c:when test="${empty recipientForm.email }">
		<h3>Add a new notification recipient</h3>
		<c:url value="/admin/recipients/add" var="url" />
	</c:when>
	<c:otherwise>
		<h3>Modify notification recipient</h3>
		<c:url value="/admin/recipients/modify/recipient" var="url" />
	</c:otherwise>
</c:choose>
<form:form method="POST" action="${url}" modelAttribute="recipientForm">
	<div class="form-group">
		<form:label path="name">Notification Recipient Name:</form:label>
		<form:input type="text" class="form-control" path="name" />
	</div>

	<div class="form-group">
		<form:label path="email">Email:</form:label>
		<c:choose>
			<c:when test="${empty recipientForm.email}">
				<form:input type="email" class="form-control" path="email" />
			</c:when>
			<c:otherwise>
				<form:input type="email" class="form-control" path="email" readonly="true" />
			</c:otherwise>
		</c:choose>
	</div>
	<div class="form-group">
		<form:label path="appIds">Apps to be notified about:</form:label>
		<p>
		<i>
			<input type="checkbox" name="select-all" id="select-all" /> <label>Select/deselect
				all</label>
		</i>
		</p>		
		<c:forEach items="${recipientForm.apps}" var="app">
			<p>
				<input type="checkbox" name="appIds" value="${app.id}"
					<c:if test="${recipientApps.contains(app.id)}">checked</c:if> /> <label>${app.name}</label>
			</p>
		</c:forEach>
	</div>

	<c:choose>
		<c:when test="${empty recipientForm.email}">
			<input class="btn btn-primary" type="submit" value="Add Notification Recipient" />
		</c:when>
		<c:otherwise>
			<input class="btn btn-primary" type="submit" value="Modify Notification Recipient" />
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