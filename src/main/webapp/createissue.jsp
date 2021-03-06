<%@page import="com.allissues.service.OfyService"%>
<%@page import="com.allissues.data.Customer"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.allissues.data.Project"%>
<%@page import="com.allissues.data.Developer"%>
<%@page import="com.googlecode.objectify.Key"%>
<%@page import="com.allissues.data.Issue"%>
<%@page import="com.allissues.service.OfyService"%>
<%@page import="com.googlecode.objectify.Objectify"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	Logger logger = Logger.getLogger("createissue.jsp");    
%>

<%
	String username = null, usertype = null, useremail = null;
	
	try	{
		username = session.getAttribute("username") == null ? "" : (String) session.getAttribute("username");
		usertype = session.getAttribute("usertype") == null ? "" : (String) session.getAttribute("usertype");
		useremail = session.getAttribute("useremail") == null ? "" : (String) session.getAttribute("useremail");
	} catch (Exception e)	{
		logger.warning("Exception in Home.jsp while retrieving session variables");
		e.printStackTrace();
	}
	
	try	{
		if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else	{			
			Customer customer = null;
			Developer developer = null;
			
			Key<Project> projectKey = null;
			Project project = null;
			
			List<String> allProjects = null;
			
			if ("developer".equalsIgnoreCase(usertype))	{
				developer = OfyService.ofy().load().key(Key.create(Developer.class, useremail)).now();
				
				if (null != developer)	{
					projectKey = developer.getProject();
					project = OfyService.ofy().load().key(projectKey).now();
					
					allProjects = developer.getProjects();
				}
				
			} else if ("customer".equalsIgnoreCase(usertype))	{
				customer = OfyService.ofy().load().key(Key.create(Customer.class, useremail)).now();
				
				if (null != customer)	{
					allProjects = customer.getAllProjects(); 
				}
				
				if (null != allProjects && allProjects.isEmpty())	{
			    	logger.warning("Customer not added to any project.");
			    	out.println("You need to be added to at-least 1 project before adding an issue");
			    	return;
				}
			}
			
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Create an Issue</title>
	
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
    
    <!-- Bootstrap Datepicker -->
    <link href="css/bootstrap-datepicker3.min.css" rel="stylesheet">
</head>
<body>

<div class="header" style="border-bottom: 1px solid; margin-bottom: 19px;">
	<jsp:include page="CommonHeader.jsp"></jsp:include>
</div>

<div class="col-sm-12">	
	<h1 class="text-center" style="margin-bottom: 25px;">Enter Issue Details</h1>
    <form action="AddIssue" method="post" class="form-horizontal" id="issue-details" name="issue-details">
        <div class="form-group" id="title-group">
            <label for="title" class="col-sm-2 control-label">Issue Title</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" name="title" placeholder="Title" />
            </div>
        </div>

        <div class="form-group" id="description-group">
            <label for="description" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10">
                <textarea rows="10" class="form-control" id="description" name="description"></textarea>
            </div>
        </div>

        <div class="form-group" id="priority-group">
            <label for="priority" class="col-sm-2 control-label">Priority</label>
            <div class="col-sm-10">
                <select class="form-control" id="priority" name="priority">
                	<option value="1">Low</option>
                	<option value="2">Medium</option>
                	<option value="3">High</option>
                </select>
            </div>
        </div>

        <% if ("developer".equalsIgnoreCase(usertype)) { %>
	        <div class="form-group" id="assigned-to-group">
	            <label for="assigned-to" class="col-sm-2 control-label">Assign To</label>
	            <div class="col-sm-10">
	                <select class="form-control" id="assigned-to" name="assigned-to">
	                	<% for (String workDeveloperStr : project.getAllDevelopers())	{
	                		Developer workDeveloper = (Developer) OfyService.ofy().load().key(Key.create(workDeveloperStr)).now();
	                	%>
	                		<option value="<%= workDeveloper.getEmail() %>"><%= workDeveloper.getName() %></option>
	                	<% } %>
	                	<option value="<%= useremail %>">-ASSIGN TO MYSELF-</option>
	                </select>
	            </div>
	        </div>
        <% } %>
        
        <div class="form-group" id="project-group">
        	<label for="project" class="col-sm-2 control-label">Select Project</label>
        	<div class="col-sm-10">
	        	<select class="form-control" id="project" name="project">
	        		<% if ("developer".equalsIgnoreCase(usertype))	{ %>
	        			<option value="<%= projectKey.getId() %>"><%= project.getName() %></option>
	        		<% } %>
	        		
	        		<% for (String strProjectKey : allProjects)	{
	        			Key<Project> workProjectKey = Key.create(strProjectKey);
	        			Project workProject = (Project) OfyService.ofy().load().key(workProjectKey).now();
	        		%>
	        			<option value="<%= workProjectKey.getId() %>"><%= workProject.getName() %></option>
	        		<% } %>
	        	</select>
	        </div>
        </div>
        
        <div class="form-group" id="res-date-group">
        	<label for="res-date" class="col-sm-2 control-label">Resolution Date</label>
        	<div class="col-sm-10">
        		<input type="text" class="form-control datepicker" id="res-date" name="res-date" />
        	</div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-6 col-sm-10">
                <button type="submit" class="btn btn-success" id="create-issue-button" name="create-issue-button">Add Issue</button>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="alert alert-danger" id="error"></div>
            </div>
        </div>
    </form>
</div>

<!-- jQuery -->
<script type="text/javascript" src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!--Bootstrap Datepicker JavaScript -->
<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script>

<!-- TinyMCE -->
<script type="text/javascript" src="tinymce/tinymce.min.js"></script>

<script type="text/javascript">
	$('#project').on('change', function ()	{
		if ($(this).find(':selected').val() == '<%= projectKey.getId() %>')	{
			$('#assigned-to').attr('disabled', 'disabled');
		} else {
			$('#assigned-to').removeAttr('disabled');
		}
	});
</script>

<!-- Custom JavaScript -->
<script type="text/javascript" src="js/createissue.js"></script>

</body>
</html>
<% 
		}
	} catch (Exception e)	{
		logger.warning("Exception on page createissue.jsp. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
		for (StackTraceElement elem : e.getStackTrace())	{
logger.warning(elem.toString());
		}
		e.printStackTrace();
	}
%>
