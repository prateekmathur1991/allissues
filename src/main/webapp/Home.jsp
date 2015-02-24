<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Home</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
    
    <style type="text/css">
    	header	{
    		background: #D3E3E8; 
			font-family: "Cambria", Times, serif;
			text-align: center;
    	}
    	
    	header nav	{
    		font-size: 14px;
    		font-family: "Calibri", Times, serif;
    		text-align: center;
    	}
    </style>
</head>

<body>
<header>
	<h1>All Issues</h1>   
    <h3>A centralized issue tracking system, for customers and dev teams. </h3>
    
    <nav>
    	Welcome, <a href=""></a>
    </nav>
</header>

<section id="search">
	<form action="/Search" method="get">
		<p>
			<label for="searchIssue">Search Issues: &nbsp;</label>
			<input type="search" id="searchIssue" name="searchIssue" />
		
			<input type="submit" value="Search" id="searchBtn" name="searchBtn" />
		</p>
	</form>
</section>

<section id="issue">
	<a href="createissue.jsp">Create Issue</a>
</section>

</body>
</html>