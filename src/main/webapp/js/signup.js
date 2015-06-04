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
 * jQuery methods for real-time and on submission form validation
 *
 * @author Prateek Mathur
 */

// Global variable to keep track of errors
var hasError = true;

// Name can't be empty
$('#name').on('input', function()   {
    var input = $(this);
    var is_name = input.val();
    if(is_name) {
        $('#namegroup').removeClass("has-error").addClass("has-success");
        hasError = false;
    } else  {
        $('#namegroup').removeClass("has-success").addClass("has-error");
        hasError = true;
    }
});

// Email has to be valid
$('#email').on('input', function()  {
    var input = $(this);
    var re = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
    var is_email = re.test(input.val());
    if(is_email)    {
        $('#emailgroup').removeClass("has-error").addClass("has-success");
        hasError = false;
    }
    else    {
        $('#emailgroup').removeClass("has-success").addClass("has-error");
        hasError = true;
    }
});


// Password cannot be left blank
$('#pass').on('input', function()   {
    var input = $(this);
    var is_name = input.val();
    if(is_name) {
        $('#passgroup').removeClass("has-error").addClass("has-success");
        hasError = false;
    } else  {
        $('#passgroup').removeClass("has-success").addClass("has-error");
        hasError = true;
    }
});

// Confirm Password box cannot be empty and should be equal to password
$('#confirmpass').on('input', function()    {
    var pass = $('#pass').val();
    var confirm = $('#confirmpass').val();

    if (confirm)    {
        if (confirm != pass)    {
            $('#confirmpassgroup').removeClass("has-success").addClass("has-error");
            hasError = true;
        } else  {
            $('#confirmpassgroup').removeClass("has-error").addClass("has-success");
            hasError = false;
        }
    } else  {
        $('#confirmpassgroup').removeClass("has-success").addClass("has-error");
        hasError = true;
    }
});


// On Submission Form Validation
$("#signupbutton").click(function(event){
     if (hasError)    {
        event.preventDefault();
        $('#error').show();
     }
});