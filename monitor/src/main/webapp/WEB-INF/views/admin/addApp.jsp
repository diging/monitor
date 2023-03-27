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
		<form:label path="tagString">Tags</form:label>
		<input type="text" class="form-control" id="tagString" />
		<div class="tags-container" id="tags-container" style="display: flex; flex-wrap: wrap;">
			<ul class="list-group" id="tags-list"></ul>
		</div>
		<form:hidden path="tagString" id="hidden-tags"/>
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
	
	$("#tagString").autocomplete({
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
							return item.name;
						}));
					}
				});
			},
			minLength : 1,
			delay : 500,

			//define select handler
			select : function(event, ui) {
				// Update the tags list in the input field
				updateTagsField(ui.item.value);

				// Clear the input field
				$(this).val("");

				// Prevent the default autocomplete behavior
				return false;
			},
			focus: function(event, ui) {
		        $(this).val(ui.item.label);
		        return false;
	        }   
		}).keydown(function(event){
			if (event.keyCode === $.ui.keyCode.ENTER) {
				event.preventDefault();
				var newTag = $(this).val();	
				updateTagsField(newTag);
				$(this).val("");
			}
		});
		
		function updateTagsField(tag) {
			if (tag.trim() != ""){
				var li = $("<li>");
				li.addClass("tag");
				li.text(tag);
				
				var deleteButton = $("<button>");
				deleteButton.addClass("btn btn-xs btn-danger");
			    deleteButton.html("<i class='glyphicon glyphicon-remove'></i>");
			    deleteButton.attr("type", "button");
			    deleteButton.click(function() {
			      var index = currentTags.indexOf(tag);
			      if (index > -1) {
			        currentTags.splice(index, 1);
			        li.remove();
			        $("#hidden-tags").val(currentTags.join(","));
			      }
			    });
			    
			    li.append(deleteButton);
		        $("#tags-list").append(li);
				
				// Update the tags list in the input field
				currentTags.push(tag);
				$("#hidden-tags").val(currentTags.join(","));
			}
		}
		
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
		
		function populateTags() {
			var tags = "${appForm.tagString}".split(",");
			for (var i = 0; i < tags.length; i++) {
			    updateTagsField(tags[i]);
			}
		}

		window.onload = function() {
			showHideGroups();
			populateTags();
		};
	});
</script>
<style>
input[type="radio"] {
	margin: 0 10px 0 10px;
}
</style>