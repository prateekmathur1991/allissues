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
 * jQuery methods for validation of project setting page, getting details of 
 * already added people and sending AJAX requests for updating project info 
 * and adding people to the project.
 *
 * @author Prateek Mathur
 */

$(document).ready(function ()	{
	$('#error').hide();
	var sel;
	
	var ms = $('#people').magicSuggest({
		allowFreeEntries: false,
		method: 'get',
		minChars: 2,
		data: '/users',
		groupBy: 'usertype',
		valueField: 'email',
		useCommaKey: true
	});
	
	$(ms).on('selectionchange', function ()	{
		sel = this.getSelection();
	});
	
	$.get('/users', {action: 'getpeople'}, function (data)	{
		$(data).each(function (i, o)	{
			// TODO
			// Add users to the list in proper format
		});
	});
	 
	$('#save-name-button').on('click', function ()	{
		if ($.trim($('#name').val()) == '')	{
			$('#error').html('Please enter a value for project name');
			$('#error').slideDown();
			$('#name-group').addClass('has-error');
			$('#name').focus();
			return false;
		} else {
			$('#name-group').removeClass('has-error');
			
			$.post('/CreateProject', {action: 'update', name: $.trim($('#name').val())}, function (data)	{
				if (data.status == 'success')	{
					$('#error').removeClass('alert-danger').addClass('alert-success');
					$('#error').html('Project Info has been updated successfully');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				} else if (data.status == 'failure')	{
					$('#error').removeClass('alert-success').addClass('alert-danger');
					$('#error').html('The server encountered an unexpected error while performing the operation. Please try again');
					$('#error').slideDown();
				}
			});
		}
	});
	
	$('#add-people-button').on('click', function ()	{
		$.post('/users', {action: 'addusers', users: JSON.stringify(sel)}, function (data)	{
			if (data.status == 'success')	{
				$('#error').removeClass('alert-danger').addClass('alert-success');
				$('#error').html('People added to project successfully');
				$('#error').slideDown();
				setTimeout(function () {
					$('#error').slideUp();
				}, 5000);
			} else if (data.status == 'failure')	{
				if (data.message == 'devExists')	{
					$('#error').removeClass('alert-success').addClass('alert-danger');
					$('#error').html('The developer ' + data.name + ' is already added to the project');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				} else if (data.message == 'custExists')	{
					$('#error').removeClass('alert-success').addClass('alert-danger');
					$('#error').html('The customer ' + data.name + ' is already added to the project');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				} else {
					$('#error').removeClass('alert-success').addClass('alert-danger');
					$('#error').html('The server encountered an unexpected error while performing the operation. Please try again.');
					$('#error').slideDown();
					setTimeout(function () {
						$('#error').slideUp();
					}, 5000);
				}
			}
		});
	});
});