$(document).ready(function() {
	$("#dijkstraForm").submit(function(e) {
		e.preventDefault();
		$.post($(this).attr("action"),
			$(this).serialize(),
			function(response) {
				var isErr = 'hasError';
				// when there are an error then show error
				if (response.indexOf(isErr) > -1) {
					alert("Error happened. Try again!")
				} else {
					$("#result").text(response);
				}
			}
		);
		return false;
	});
});