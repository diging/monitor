window.setTimeout(function() {
	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/monitor/admin/apps/reload",
		success : function(data) {
			data.forEach(update)
		},
		error : function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			console.log(err.message)
		}
	});
}, 60000);
function update(data) {
	$('#name_' + data.id).text(data.name)
	$('#desc_' + data.id).text(data.description)
	$('#url_' + data.id).text("(" + data.healthUrl + ")")
	$('#status_' + data.id).text("App status is:" + data.lastAppTest.status)

}