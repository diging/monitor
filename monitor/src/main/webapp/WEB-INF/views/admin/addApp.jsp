<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:choose>
	<c:when test="${empty appForm.id}">
		<c:url value="/admin/apps/add" var="url"/>
	</c:when>
	<c:otherwise>
		<c:url value="/admin/apps/${appForm.id}/modify" var="url"/>
	</c:otherwise>
</c:choose>
<form:form method="POST" action="${url}" modelAttribute="appForm">
  <div class="form-group">
 	 <form:label path="name">App Name:</form:label>
    	 <form:input type="text" class="form-control" path="name" />
    	 <form:errors path="name" cssClass="error" />
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
		<form:label path="tags">Tags</form:label>
		<form:input type="text" class="form-control" id="tag-input" path="tags" />
		<ul id="tag-list"></ul>
	</div>
	
	<div class="form-group">
 	 <form:label path="warningReturnCodes">Warning return codes (comma-separated):</form:label>
    	 <form:input type="text" class="form-control" path="warningReturnCodes" />
  </div>
  
  <div class="form-group">
  	 <form:label path="timeout">Method for ping:</form:label>
  	 <form:select class="form-control" path="method">
     	<form:option value="HEAD" label="HEAD" />
     	<form:option value="GET" label="GET" />
   	 </form:select>
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
  
  <c:choose>
	<c:when test="${empty appForm.id}">

	<div class="form-group">
		<form:label path="username">Username:</form:label>
		<form:input type="text" class="form-control" path="username" />
	</div>
	<div class="form-group">
		<form:label path="password">Password:</form:label>
		<form:input type="password" class="form-control" path="password" />
	</div>
	</c:when>
	<c:otherwise>
		<c:url value="/admin/apps/${appForm.id}/modifyAuth" var="modifyAuth" />
			<a href="${modifyAuth}">
			<label>Edit Authentication information</label>
			</a>
	</c:otherwise>
	</c:choose>
	<div class="form-group">
	  <form:label path="groupType">Group:</form:label>
	  <br>
	  <form:radiobutton id="groupCreate" name="group" value="NEW" path ="groupType" onclick="javascript:showHideGroups();"/>
	 	 <form:label path="groupName"><i>Create a new Group:</i></form:label>
	 	 <form:radiobutton id="groupSelect" name="group" value="EXISTING" path="groupType" onclick="javascript:showHideGroups();" />
	 	 <form:label path="existingGroupId"><i>Select from an Existing Group:</i></form:label>
	 	 <form:radiobutton id="groupNone" name="group" value="NONE" path ="groupType" onclick="javascript:showHideGroups();"/>
	 	 <label><i>None</i></label>
  	</div>
	<div class="form-group">
		<form:input type="text" class="form-control" path="groupName" style="display:none" id ="groupNew"/>
			<form:errors path="groupName" cssClass="error" />
			<form:select class="form-control" path="existingGroupId" style="display:none" id = "groupExisting">
			<form:options items="${appForm.groupIds}" var="groups" itemValue="key" itemLabel="value" />
		</form:select>
		<form:errors path="existingGroupId" cssClass="error" />
	</div>
	<div class="form-group">
		<form:label path="recipientIds">Recipients:</form:label>
		<p>
		<i>
			<input type="checkbox" name="select-all" id="select-all" /> <label>Select/deselect
				all</label>
		</i>
		</p>		
		<c:forEach items="${appForm.recipients}" var="recipient">
			<p>
				<input type="checkbox" name="recipientIds" value="${recipient.email}"
				 <c:if test="${appRecipients.contains(recipient.email)}">checked="checked"</c:if>
				/> <label>${recipient.name}</label>
			</p>
		</c:forEach>
  </div>
  <c:choose>
  	<c:when test="${empty appForm.id}">
  		<input class="btn btn-primary" type="submit" value="Add App"/>
  	</c:when>
  	<c:otherwise>
  		<input class="btn btn-primary" type="submit" value="Modify App"/>
  	</c:otherwise>
  </c:choose>
</form:form>
<script>
	
	const existingTags = ["tag1", "tag2", "tag3"];
	
	$('#tag-input').autocomplete({
	    source: existingTags
	    
	  });
	
	// Add new tag to the list
	  function addTag(newTag) {
	    if (existingTags.includes(newTag)) {
	      alert('This tag already exists. Please select it from the list.');
	      return;
	    }
	    existingTags.push(newTag);
	    $('#tag-list').append(`<li>${newTag} <button class="remove-tag">X</button></li>`);
	    $('#tag-input').val('');
	  }

	  // Remove tag from the list
	  $('#tag-list').on('click', '.remove-tag', function() {
	    $(this).parent().remove();
	  });

	  // Add new tag when the user presses enter in the input field
	  $('#tag-input').keypress(function(e) {
	    if (e.which == 13) {
	      e.preventDefault();
	      addTag($(this).val());
	    }
	  });
	
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
	function showHideGroups() {
		if (document.getElementById('groupCreate').checked) {
			$('#groupNew').css('display', 'block');
			$('#groupExisting').css('display', 'none');

		} else if (document.getElementById('groupSelect').checked) {
			$('#groupNew').css('display', 'none');
			$('#groupExisting').css('display', 'block');
		} else {
			$('#groupNew').css('display', 'none');
			$('#groupExisting').css('display', 'none');
		}
	}
	
	window.onload = function() {
		showHideGroups();
	};
</script>
<style>
input[type="radio"] {
	margin: 0 10px 0 10px;
}
</style>