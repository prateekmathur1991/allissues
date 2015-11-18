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

$('#add-comment-button').on('click', function ()	{
	var check = true;
	
	var title = $.trim($('#comment-title').val());
	var body = $.trim($('#comment-body').val());
	
	if (title == '')	{
		$('#title-group').addClass('has-error');
		$('#comment-title').focus();
		$('#issue-alert').addClass('alert-danger').removeClass('alert-success');
		$('#issue-alert').html('Please add comment title');
		$('#issue-alert').slideDown();
		setTimeout(function ()	{
			$('#issue-alert').slideUp();
		}, 5000);
		check = false;
		return false;
	} else {
		$('#title-group').removeClass('has-error');
		$('#issue-alert').html('');
		$('#issue-alert').slideUp();
	}
	
	if (body == '')	{
		$('#body-group').addClass('has-error');
		$('#comment-body').focus();
		$('#issue-alert').addClass('alert-danger').removeClass('alert-success');
		$('#issue-alert').html('Please add comment body');
		$('#issue-alert').slideDown();
		setTimeout(function ()	{
			$('#issue-alert').slideUp();
		}, 5000);
		check = false;
		return false;
	} else {
		$('#body-group').removeClass('has-error');
		$('#issue-alert').html('');
		$('#issue-alert').slideUp();
	}
	
	if (check)	{
		$.post('/AddIssue', {action: 'addcomment', title: title, body: body}, function (response)	{
			
		});
	}
});
