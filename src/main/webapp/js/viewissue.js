$(document).ready(function ()	{
	$('#issue-alert').hide();
});

$('#close').on('click', function ()	{
	$.post('/AddIssue', {action: 'close', id: $(this).data('issueid'), projectid: $(this).data('projectid')}, function (response)	{
		response = response.replace(/\n\r/g, '').replace(/\n/g, '').replace(/\r/g, '');

		if (response == 'closeSuccess')	{
			$('#issue-alert').addClass('alert-success').removeClass('alert-danger');
			$('#issue-alert').html('Issue closed successfully');
			$('#issue-alert').slideDown();
			setTimeout(function ()	{
				$('#issue-alert').slideUp();
			}, 5000);
		} else if (response == 'closeError') {
			$('#issue-alert').addClass('alert-danger').removeClass('alert-success');
			$('#issue-alert').html('The server encountered an error while performing the operation. Please try again.');
			$('#issue-alert').slideDown();
			setTimeout(function ()	{
				$('#issue-alert').slideUp();
			}, 5000);
		}
	});
});
