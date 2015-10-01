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


$(document).ready(function (e) {
	
	$('#error').hide();
	
	$('#save-password-button').on('click', function (e) {
		e.preventDefault();
		
		var check = true;
		$('#password-group input').each(function () {
			if ($(this).attr('id') == 'oldpass' || $(this).attr('id') == 'newpass') {
				if ($.trim($(this).val()) == '') {
					$(this).closest('.form-group').addClass('has-error');
					$('#error').addClass('alert-danger').removeClass('alert-success');
					$('#error').html('This field cannot be left blank');
					$('#error').slideDown();
					$(this).focus();
					check = false;
					return false;
				} else {
					$(this).closest('.form-group').removeClass('has-error');
					$('#error').html('');
					$('#error').slideUp();
				}
			} else if ($(this).attr('id') == 'confpass') {
				if ($.trim($('#newpass').val()) != $.trim($('#confpass').val())) {
					$('#confpass').closest('.form-group').addClass('has-error');
					$('#error').addClass('alert-danger').removeClass('alert-success');
					$('#error').html('The passwords do not match. Please enter the same value for new and confirm password');
					$('#error').slideDown();
					$(this).focus();
					check = false;
					return false;
				}
			}
		});
		
		if (check) {
			$.post('/update', {action: 'updatepass', oldpass: $('#oldpass').val(), newpass: $('#newpass').val()}, function (data) {
				if (data.status == 'success') {
					$('#error').addClass('alert-success').removeClass('alert-danger');
					$('#error').html('Your password has been updated successfully.');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				} else if (data.status == 'failure') {
					if (data.reason == 'oldnomatch') {
						$('#error').addClass('alert-danger').removeClass('alert-success');
						$('#error').html('The current password you have entered is not correct. Please enter the password you are using currently');
						$('#error').slideDown();
						$('#oldpass').focus();
						setTimeout(function () {
							$('#error').slideUp();
						}, 5000);
					}
				}
			});
		}
	});
	
	$('#save-name-button').on('click', function () {
		var check = true;
		if ($.trim($('#name').val()) == '') {
			$('#error').addClass('alert-danger').removeClass('alert-success');
			$('#error').html('Please enter a value for display name');
			$('#error').slideDown();
			$('#name').focus();
			$('#name').closest('.form-group').addClass('has-error');
			check = false;
			return false;
		} else {
			$('#error').html('');
			$('#error').slideUp();
		}
		
		if (check) {
			$.post('/update', {action: 'updatename', name: $('#name').val()}, function (data) {
				if (data.status == 'success') {
					$('#error').addClass('alert-success').removeClass('alert-danger');
					$('#error').html('Your display name has been updated successfully.');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				} else if (data.status == 'failure') {
					$('#error').addClass('alert-danger').removeClass('alert-success');
					$('#error').html('The server encountered an error while performing the operation. Please try again');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				}
			});
		}
	});
});
