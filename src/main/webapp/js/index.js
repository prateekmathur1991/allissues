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
 * Utility methods for All Issues front end.
 * 
 * @author Prateek Mathur
 */

// jQuery to collapse the navbar on scroll
$(window).scroll(function() {
    if ($(".navbar").offset().top > 50) {
        $(".navbar-fixed-top").addClass("top-nav-collapse");
    } else {
        $(".navbar-fixed-top").removeClass("top-nav-collapse");
    }
});

// jQuery for page scrolling feature - requires jQuery Easing plugin
$(function() {
    $('a.page-scroll').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1500, 'easeInOutExpo');
        event.preventDefault();
    });
});

// Closes the Responsive Menu on Menu Item Click
$('.navbar-collapse ul li a').click(function() {
    $('.navbar-toggle:visible').click();
});

// Adds an onclick click handler the 'Get Started' button
$('#getStarted').click(function()	{
	window.location.href = 'http://' + window.location.host + '/signup.html';
});

// Prevents the login box on home page from closing when clicked
$('#loginBox').click(function(e)	{
	e.stopPropagation();
});

// Delegate the default form submission action to our method
$('#loginFrm').on('submit', loginAction);

// JS Function to execute on form submission
function loginAction(event)	{
	event.preventDefault();
	
	var userEmail = $('#email').val();
	var userPassword = $('#password').val();
	
	if (userEmail == "")	{
		$('#error-box').html("Please enter your email");
		$('#email').focus();
		return false; 
	}
	
	if (userPassword == "")	{
		$('#error-box').html("Please enter your password");
		$('#password').focus();
		return false;
	}
	
	var credentials = $('#loginFrm').serialize();
	
	$.post("/login", credentials, function (data)	{
	    if (data.status == "success")	{
	    	window.location.href = 'http://' + window.location.host + '/' + data.forwardUrl;
	    } else if (data.status = "faliure") {	
	    	$('#error-box').html("The username or password is incorrect. Please try again");
	    	$('#email').focus();
	    }
	});
}
