<%@page import="java.util.List"%>
<%@page import="com.allissues.data.Project"%>
<%@page import="com.googlecode.objectify.ObjectifyService"%>
<%@page import="com.googlecode.objectify.Key"%>
<%@page import="com.googlecode.objectify.Objectify"%>
<%@page import="com.allissues.data.Customer"%>
<%@page import="com.allissues.data.Developer"%>
<%@page import="com.google.appengine.repackaged.com.google.io.base.shell.ExecFailedException"%>
<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	static Logger logger = Logger.getLogger("projectsettings.jsp");
%>

<%
	String username = null, usertype = null, useremail = null;
	
	try	{
		username = session.getAttribute("username") == null ? "" : (String) session.getAttribute("username");
		usertype = session.getAttribute("usertype") == null ? "" : (String) session.getAttribute("usertype");
		useremail = session.getAttribute("useremail") == null ? "" : (String) session.getAttribute("useremail");
	} catch (Exception e)	{
		logger.warning("Exception on page projectsettings.jsp while retrieving session variables");
		e.printStackTrace();
	}
	
	try {
		if ("".equals(username) || "".equals(usertype) || "".equals(useremail)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else if ("customer".equalsIgnoreCase(usertype)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		} else {
			Objectify ofy = ObjectifyService.ofy();
			
			String projectName = "";
			Project project = null;
			
			Developer developer = ofy.load().key(Key.create(Developer.class, useremail)).now();
			if (null != developer)	{
				Key<Project> projKey = developer.getProject();
				if (null != projKey)	{
					project = ofy.load().key(projKey).now();
					logger.info("Got project:: " + project);
					projectName = project == null ? "" : project.getName();
				} else {
					logger.warning("project Key found null");
				}
				
			} else {
				logger.warning("developer found null");
			}
			
			logger.info("Got projectName:: " + projectName);
%>
<!Doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Project Settings</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
</head>

<body>

<div class="header" style="border-bottom: 1px solid; margin-bottom: 19px;">
	<jsp:include page="CommonHeader.jsp"></jsp:include>
</div>

<div class="container-fluid">
	<form class="form-horizontal" id="project-form" name="project-form" method="post">
		<div class="form-group">
  			<h4 class="text-info col-sm-12 col-sm-offset-2">Project Settings</h4>
  		</div>
		
		<div id="display-name-group">	
			<div class="form-group" id="name-group">
	            <label for="name" class="col-sm-2 control-label" style="text-align: left;">Project Name</label>
	            <div class="col-sm-10">
	                <input type="text" class="form-control" id="name" name="name" value="<%= projectName %>" placeholder="Project Name" />
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <div class="col-sm-10 col-sm-offset-2">
	                <button type="button" class="btn btn-success" id="save-name-button" name="save-name-button">Update</button>
	            </div>
	        </div>
	    </div>
        
        <div id="people-group">
	        <div class="form-group">
	  			<h4 class="text-info col-sm-12 col-sm-offset-2">Add people to your Project</h4>
	  		</div>
	
	        <div class="form-group" id="old-pass-group">
	            <div class="col-sm-12 col-sm-push-2">
	                <input type="text" class="form-control" id="people" name="people" placeholder="Type Email IDs of developers or customers you want to add to this project" />
	            </div>
	        </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <button type="button" class="btn btn-success" id="add-people-button" name="add-people-button">Add</button>
            </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="alert alert-danger" id="error"></div>
            </div>
        </div>
	</form>
</div> <!-- .container-fluid -->

<!-- jQuery -->
<script type="text/javascript" src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!-- Custom JavaScript -->
<script type="text/javascript" src="js/projectsettings.js"></script>
</body>
</html>
<% 
		}
	} catch (Exception e) {
		logger.warning("Exception on page projectsettings.jsp");
		e.printStackTrace();
	}
%>
