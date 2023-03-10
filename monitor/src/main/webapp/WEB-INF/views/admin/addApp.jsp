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
		<input type="text" class="form-control" name="tags" id="tags" />
		<ul class="dropdown-list" id="tags-list"></ul>
		<form:hidden path="tags" id="hidden-tags"/>
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
$(document).ready(function() {
	var currentTags = [];
	
	$("#tags").autocomplete({
			//source:tags,
			source : function(request, response) {
				$.ajax({
					url : "${pageContext.request.contextPath}/admin/apps/tags/getTagList",
					dataType : "json",
					data : {
						term : request.term
					},
					success : function(data) {
						response($.map(data, function(item){
							return item.name;		//Change the controller to only return tag names
						}));
					}
				});
			},
			minLength : 1,
			delay : 500,

			//define select handler
			select : function(event, ui) {
				// Add the selected tag to the tags array
				console.log("Ui Item Value");
				console.log(ui.item.val);
				currentTags.push(ui.item.val);

				// Update the tags list in the input field
				updateTagsField(ui.item.val);

				// Clear the input field
				$(this).val("");

				// Prevent the default autocomplete behavior
				return false;
			},
			focus: function(event, ui) {
		        $(this).val(ui.item.name);
		        return false;
	        }   
		});
		
		$("#tags").on("keydown", function(event) {
			if (event.keyCode === $.ui.keyCode.ENTER) {
				console.log("ENTER event triggered!");
				// Add the new tag to the tags array
				var newTagName = $(this).val();
				if (newTagName !== "") {
					console.log("New Tag Name");
					console.log(newTagName);
					currentTags.push(newTagName);
					// Update the tags list in the input field
					updateTagsField(newTagName);
					// Clear the input field
					$(this).val("");
				}
				return false;
			}
		});
		
		function updateTagsField(tag) {
			
			var li = $("<li>").text(tag);
	          $("#tags-list").append(li);
			
			// Update the tags list in the input field
			console.log("Current Tags are: ");
		    console.log(currentTags);
			$("#hidden-tags").val(currentTags.join(","));
			console.log($("#hidden-tags").val());
			console.log("Type is ");
			console.log(typeof $("#hidden-tags").val());
		}	

		// Remove tag from the list

		// Add new tag when the user presses enter in the input field

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
	});
</script>
<style>
input[type="radio"] {
	margin: 0 10px 0 10px;
}
</style>