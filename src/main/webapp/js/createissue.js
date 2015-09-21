$(document).ready(function () {
	$('.datepicker').datepicker({
		startDate: new Date()
	});
	
	tinymce.init({
        selector: "#description"
    });
	
	$('#error').hide();
	
	$('#create-issue-button').on('click', function (e)	{
		e.preventDefault();
		
		var check = false;
		$('#issue-details input[type=text]').each(function () {
			if ($.trim($(this).val()) == '')	{
				$(this).closest('.form-group').addClass('has-error');
				$(this).focus();
				
				$('#error').html("Please enter a value for this field.");
				$('#error').slideDown();
				check = false;
				return false;
			} else {
				$(this).closest('.form-group').removeClass('has-error');
				$('#error').slideUp();
				check = true;
			}
		});
		
		// Submit the Form after successful validation
		if (check)	{
			$('#issue-details').submit();
		}
	});
});