<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | View Issue Details</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
	
	<style type="text/css">
		header	{
    		background: #D3E3E8; 
			font-family: "Cambria", Times, serif;
			text-align: center;
    	}
	</style>
	
</head>
<body>
<header>
	<h1>All Issues</h1>   
    <h3>A centralized issue tracking system, for customers and dev teams. </h3>
</header>

<section id="issue">
	<table id="titleBar">
		<tr>
			<td id="title" colspan="5" style="font-weight: bold; font-size=14px">Issue Title Here</td>
			<td id="status">Issue Status (Open/Closed)</td>
			<td id="priority">Issue Priority Level</td>
		</tr>
	</table>
	
	<section id="description">
		<p style="font-weight: bold; font-size=14px">Description:</p>
		<p>Issue Description Goes here</p>
	</section>
	
</section>
</body>
</html>