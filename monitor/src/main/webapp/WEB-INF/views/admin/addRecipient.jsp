<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h3>Add a new notification recipient</h3>
<c:url value="/admin/recipients/add" var="url"/>
<form:form method="POST" action="${url}" modelAttribute="recipientForm">
  <div class="form-group">
 	 <form:label path="name">Notification Recipient Name:</form:label>
    	 <form:input type="text" class="form-control" path="name" />
  </div>
  
  <div class="form-group">
 	 <form:label path="email">Email:</form:label>
    	 <form:input type="email" class="form-control" path="email" />
  </div>
  <div class="form-group">
 	 <form:label path="apps">Apps to be notified about:</form:label>
 	 	 <c:forEach items="${recipientForm.apps}" var="app">
 	 	 <%-- <form:checkboxes element="li" path="apps" items="${recipientForm.apps}"/> --%>
	 	 	 <li>
	 	 	 	<input type="checkbox"  name="apps" value="${app.id}" checked="checked"/>
	 	 	 	<label>${app.name}</label>
	 	 	 </li>
 	 	 </c:forEach>
  </div>
  
  <input class="btn btn-primary" type="submit" value="Add Notfication Recipient"/>
</form:form>