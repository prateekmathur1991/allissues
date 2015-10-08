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
 * jQuery methods for validation of project setting page, and sending AJAX requests 
 * for updating project name and adding people to the project.
 *
 * @author Prateek Mathur
 */

$(document).ready(function ()	{
	$('#error').hide();
	
	$('#save-name-button').on('click', function ()	{
		if ($.trim($('#name').val()) == '')	{
			$('#error').html('Please enter a value for project name');
			$('#error').slideDown();
			$('#name-group').addClass('has-error');
			$('#name').focus();
			return false;
		} else {
			$('#name-group').removeClass('has-error');
		}
	});
	
	$('#add-people-button').on('click', function ()	{
		// TODO
		// Find and add a good jQuery autocomplete plugin for this functionality.
	});
});