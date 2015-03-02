<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Sign Up</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
	
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.1/jquery.validate.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function()	{
			$("#registerForm").validate({
        		rules: {
            		confirm_password: {
                		equalTo: "#password"
            		}
        		},
				messages:	{
					confirm_password : "Passwords do not match"
				}
			});
		});
    		
	</script>
	
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
	
	
	
</head>

<body>

<header>
	<h1>All Issues</h1>   
    <h3>A centralized issue tracking system, for customers and dev teams. </h3>
</header>

<section id="register">
	<form action="/register" method="post" id="registerForm">
		<p>
			<label for="type">You are a:&nbsp;</label>
			<select id="type" name="type">
				<option selected="selected">Developer</option>
				<option>Customer</option>
			</select>
		</p>
		<p>
			<label for="email">Enter a valid email id:</label>
			<input type="email" id="userEmail" name="userEmail" />
		</p>
		<p>
			<label for="firstName">Enter your first name:</label>
			<input type="text" id="firstName" name="firstName" required pattern=".{2,}" title="First Name must be atleast 2 characters long" />
		</p>
		<p>
			<label for="lastName">Enter your last name:</label>
			<input type="text" id="lastName" class="userName" required pattern=".{2,}" title="Last Name must be atleast 2 characters long" />
		</p>
		<p>
			<label for="password">Choose a password:</label>
			<input type="password" id="password" name="password" required pattern=".{6,}" 
				title="Password must be atleast 6 characters long" />
		</p>
		<p>
			<label for="confirm_password">Confirm password:</label>
			<input type="password" id="confirm_password" name="confirm_password" required />
		</p>
		<p>
			<input type="submit" value="Submit" id="regSubmit" name="regSubmit" style="width: 107px; "/>
		</p>
	</form>
</section>

</body>
</html>