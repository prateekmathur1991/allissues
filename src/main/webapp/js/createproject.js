$(document).ready(function (e)	{
	$('#create-project-button').on('click', function(e)	{
		e.preventDefault();
		var check = true;
		
		$('#error').hide();
		
		$('#project-details').find('input, textarea').each(function ()	{
			if ($.trim($(this).val()) == '')	{
				$(this).closest('.form-group').addClass('has-error');
				$(this).focus();
				check = false;
				$('#error').slideDown();
				$('#error').html('Please enter a value for this field');
				return false;
			} else {
				$(this).closest('.form-group').removeClass('has-error');
				$('#error').slideUp();
				$('#error').html('');
			}
		});
		
		if (check)	{
			$('#project-details').submit();
		}
	});
});