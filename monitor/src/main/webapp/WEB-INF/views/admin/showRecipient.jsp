<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="time"%>

<h3>This is a list of all people being notified:</h3>
<a href="<c:url value="/admin/recipients/add"/>" class="btn btn-primary">Add Recipient</a>
<table class="table">
	<thead>
	<th scope="col">Name</th>
    <th scope="col">Email</th>
	</thead>
    <c:forEach items="${rcpts}" var="rcpt">
        <tr>
            <td><c:out value="${rcpt.name}"/></td>
            <td><c:out value="${rcpt.email}"/></td>  
        </tr>
    </c:forEach>
</table>
