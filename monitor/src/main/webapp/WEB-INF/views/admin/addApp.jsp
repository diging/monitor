<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/admin/apps/add" var="url"/>
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
  
  <input class="btn btn-primary" type="submit" value="Add App"/>
</form:form>