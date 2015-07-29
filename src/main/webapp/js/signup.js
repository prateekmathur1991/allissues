/*  Copyright 2015 Prateek Mathur

    All Issues is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    All Issues is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with All Issues. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * jQuery methods for real-time and on-submission form validation, and sending AJAX requests for email
 * verification and registration
 *
 * @author Prateek Mathur
 */

// Global variable to keep track of errors
var hasError;

// Email has to be valid
$('#email').on('input', function()  {
    var input = $(this);
    var re = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    var is_email = re.test(input.val());
    if(is_email)    {
        $('#emailgroup').removeClass("has-error").addClass("has-success");
        $('#error').hide();
        hasError = false;
    }
    else    {
        $('#emailgroup').removeClass("has-success").addClass("has-error");
        $('#error').html("<strong>Please enter a valid email ID.</strong>");
        $('#error').show();
        hasError = true;
    }
});

// Confirm Password box should be equal to password
$('#confirmpass').on('input', function()    {
    var pass = $('#pass').val();
    var confirm = $('#confirmpass').val();

    if (confirm != pass)    {
        $('#confirmpassgroup').removeClass("has-success").addClass("has-error");
        $('#error').html("<strong>Please enter the same password both times in password and confirm password.</strong>");
        $('#error').show();
        hasError = true;
    } else  {
        $('#confirmpassgroup').removeClass("has-error").addClass("has-success");
        $('#error').hide();
        hasError = false;
    }
    
});

$('#pass').on('input', function () {
	if ($(this).val() == "")	{
		var pass = $('#pass').val();
	    var confirm = $('#confirmpass').val();
	
	    if (confirm != pass)    {
	        $('#passgroup').removeClass("has-success").addClass("has-error");
	        $('#error').html("<strong>Please enter the same password both times in password and confirm password.</strong>");
	        $('#error').show();
	        hasError = true;
	    } else  {
	        $('#passgroup').removeClass("has-error").addClass("has-success");
	        $('#error').hide();
	        hasError = false;
	    }
	}
});

// On Submission Form Validation
$('#signupbutton').on('click', function (event) {
	event.preventDefault();
	
	$(':input').each(function (i, o)	{
		if($(this).val() == "")	{
			hasError = true;
			$(this).focus();
			$('#error').html("<strong>Some fields in your form are empty. Please fill them and try again.</strong>");
			$('#error').show();
			return false;
		} else	{
			$('#error').html("");
			$('#error').hide();
		}
	});
	
	if (hasError == true)    {
        $('#error').show();
    } else {
    	$('#error').hide();
    	
    	var credentials = $('#details').serialize();
    	
    	$.post("/register", credentials, function (data) {
    		if (data.status == "failure")	{
    			if (data.error == "developerExists")	{
    				$('#error').html("<strong>A developer account with this email already exists. Please use another email and try again.</strong>");
    				$('#error').show();
    			} else if (data.error == "customerExists")	{
    				$('#error').html("<strong>A customer account with this email already exists. Please use another email and try again.</strong>");
    				$('#error').show();
    			}
    		} else if (data.status == "success") {
		       window.location.href = 'http://' + window.location.host + '/' + data.forwardUrl;
		}
    	});
    }
});
