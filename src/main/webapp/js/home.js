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
	window.location.href = window.location + "signup.html";
});

// Add an onlick handler to the login button
$('#btnLogin').on('click', loginAction);

// Prevents the login box on home page from closing when clicked
$('#loginBox').click(function(e)	{
	e.stopPropagation();
});

// JS Function to execute on login button click event
function loginAction()	{
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
	
	// var credentials = $('#loginFrm').serialize();
	// alert(credentials);
	
	$.ajax({
		url: "login",
		
		data:	{
			email: userEmail,
			password: userPassword
		},
		
		type: "POST",
		
		dataType: "json"
	}).done(function (response)	{
		alert(response);
		$('#error-box').html(response);
	}).fail(function ()	{
		alert("Sorry! The request failed");	
	}).always(function () {
		alert("This will be executed always");
	});
}