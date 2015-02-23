<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Sign Up</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
	
	<style type="text/css">
		body	{
			padding : 0px;
			margin : 0px;
		}

		header	{
			text-align : center;
			background : #D3E3E8; 
			font-family : "Cambria", Times, serif;
			font-size : 16px;
		}
		
		section	{
			text-align : center;
			font-family : "Calibri", Times, serif;
		}
	</style>
	
	<script type="text/javascript">
		window.onload = function()	{
			document.getElementById("password2").onchange = validatePassword;
		}
		
		function validatePassword()	{
			var pass1 = document.getElementById("password1").toString();
			var pass2 = document.getElementById("password2").toString();
			
			if (pass1 != pass2)	{
				document.getElementById("password2").setCustomValidity("Passwords do not match");
			}
			else	{
				document.getElementById("password2").setCustomValidity('');
			}
		}
	</script>
	
</head>

<body>

<header>
	<h1>All Issues</h1>   
    <h3>A centralized issue tracking system, for customers and dev teams. </h3>
</header>

<section id="register">
	<form action="/register" method="post" id="registerForm">
		<label for="email">Enter a valid email id:</label>
		<input type="email" id="email" required="required" /><br><br>
		
		<label for="firstName">Enter your first name:</label>
		<input type="text" id="firstName" required="required" /><br><br>
		
		<label for="lastName">Enter your last name:</label>
		<input type="text" id="lastName" required="required" /><br><br>
		
		<label for="password1">Choose a password:</label>
		<input type="password" id="password1" required="required" /><br><br>
		
		<label for="password2">Confirm password:</label>
		<input type="password" id="password2" required="required" /><br><br>
		
		<input type="submit" value="Submit" id="regSubmit" style="width: 107px; "/>
	</form>
</section>

</body>
</html>