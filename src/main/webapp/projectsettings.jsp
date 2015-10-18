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
			logger.info("Got ofy " + ofy.toString());
			
			String projectName = "";
			Project project = null;
			
			logger.info("Loading developer now");
			Developer developer = ofy.load().key(Key.create(Developer.class, useremail)).now();
			if (null != developer)	{
				logger.info("Got developer:: " + developer.toString());
				Key<Project> projKey = developer.getProject();
				logger.info("Got proj key:: " + projKey.toString());
				if (null != projKey)	{
					logger.info("Loading project now");
					project = ofy.load().key(projKey).now();
					logger.info("Got project:: " + project);
					projectName = project == null ? "" : project.getName();
					logger.info("Got project name");
				} else {
					logger.warning("project Key found null");
					response.sendRedirect("/createproject.jsp");
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
    
    <!-- MagicSuggest -->
    <link href="css/magicsuggest-min.css" rel="stylesheet">
</head>

<body>

<div class="header" style="border-bottom: 1px solid; margin-bottom: 19px;">
	<jsp:include page="CommonHeader.jsp"></jsp:include>
</div>

<div class="container-fluid">
	<form id="project-form" name="project-form" method="post">
		<div style="margin-bottom: 30px;">
  			<h4 class="text-info col-sm-12 col-sm-offset-2">Project Settings</h4>
  		</div>
		
		<div id="display-name-group">	
			<div class="form-group" id="name-group">
	            <label for="name">Project Name</label>
	            <input type="text" class="form-control" id="name" name="name" value="<%= projectName %>" placeholder="Project Name" />
	        </div>
	        
	        <div style="margin-bottom: 30px;">
	            <div class="col-sm-10 col-sm-offset-2">
	                <button type="button" class="btn btn-success" id="save-name-button" name="save-name-button">Update</button>
	            </div>
	        </div>
	    </div>
        
        <div id="people-group">
			<div>
		  		<h4 class="text-info col-sm-12 col-sm-offset-2">Add people to your Project</h4>
		  	</div>
		
		    <div id="add-people-group">
				<div class="row">
					<div class="col-sm-6">
				        <p class="text-muted">Type Email IDs or names of developers or customers you want to add to this project</p>
			            <div>
			                <div class="row">
			                	<div class="col-xs-6">
			                		<input class="magicsuggest form-control" type="text" id="people" name="people" />
			                	</div>
			                	
			                	<div class="col-xs-6">
			                		<button type="button" class="btn btn-success" id="add-people-button" name="add-people-button">Add</button>
			                	</div>
			                </div>
			            </div>
		            </div>
		            
		            <div id="existing-users"  class="col-sm-6">
						
		            </div>
		    	</div>
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

<!-- MagicSuggest JS -->
<script type="text/javascript" src="js/magicsuggest-min.js"></script>

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
