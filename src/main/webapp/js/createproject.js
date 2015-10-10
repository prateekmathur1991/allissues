$(document).ready(function (e)	{
	
	tinymce.init({
        selector: "#description"
    });
	
	$('#create-project-button').on('click', function(e)	{
		e.preventDefault();
		var check = true;
		
		$('#error').hide();
		
		$('#project-details').find('input').each(function ()	{
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
		
		if (tinyMCE.get('description').getContent() == '')	{
			$('#error').slideDown();
			$('#error').html('Please enter a value for description');
			check = false;
		}
		
		if (check)	{
			$('#project-details').submit();
		}
	});
});