<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Create an Issue</title>
	
	<style type="text/css">
		header	{
    		background: #D3E3E8; 
			font-family: "Cambria", Times, serif;
			text-align: center;
    	}
    	
    	#createIssueForm	{
    		text-align: center;
    	}
	</style>
</head>
<body>

<header>
	<h1>All Issues</h1>   
    <h3>A centralized issue tracking system, for customers and dev teams. </h3>
</header>

<form action="/AddIssue" method="post" id="createIssueForm">
	<p>
		<label for="title">Issue Title</label>
		<input type="text" id="title" name="title" pattern=".{5,}" required title="Title should be longer than 15 characters" />
	</p>
	
	<p>
		<textarea rows="25" cols="25" id="description">Enter Issue Description</textarea>
	</p>
	<p>
		<label for="priority">Priority Level</label>
		<select id="priority" name="priority">
			<option selected="selected">0</option>
			<option>1</option>
			<option>2</option>
			<option>3</option>
		</select>
	</p>
	<p>
		<label for="deadline">Deadline</label>
		<input type="date" id="deadline" name="deadline" />
	</p>
	<p>
		<label for="assignTo">Assign this issue to:</label>
		<input type="text" name="assignTo" id="assignTo" />
	<p>
		<input type="submit" value="Add Issue" id="addIssue" name="addIssue" />
	</p>
</form>

</body>
</html>