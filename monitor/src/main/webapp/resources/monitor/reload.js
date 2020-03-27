window.setInterval(function() {
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "admin/apps/reload",
		success : function(data) {
			data.forEach(update)
		},
		error : function(xhr, status, error) {
			console.error(xhr.responseText)
			console.error(status)
			console.error(error.message)
			alert('This page could not be updated')
		}
	});
}, 60000);
function update(data) {
	$('#name_' + data.id).text(data.name)
	$('#desc_' + data.id).text(data.description)
	$('#url_' + data.id).text(data.healthUrl)
	$('#status_' + data.id).text("App status is: " + data.lastAppTest.status)

}