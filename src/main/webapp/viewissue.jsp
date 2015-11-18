<%@page import="java.io.StringWriter"%>
<%@page import="com.allissues.data.Project"%>
<%@page import="com.googlecode.objectify.Key"%>
<%@page import="com.allissues.data.Issue"%>
<%@page import="com.googlecode.objectify.ObjectifyService"%>
<%@page import="com.googlecode.objectify.Objectify"%>
<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	static Logger logger = Logger.getLogger("viewissue.jsp");
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
		// The absence of session variables denotes that nobody is logged in
		if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else	{
			if ("customer".equalsIgnoreCase(usertype))	{
				// Customers cannot view a issue created by a developer
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} else {
				long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
				long projectid = request.getParameter("projectid") == null ? 0 : Long.parseLong(request.getParameter("projectid"));
				logger.info("ID:: " + id + " Parent Project ID:: " + projectid);
				
				Key<Issue> issueKey = null;
				Issue issue = null;
				
				if (id != 0 && projectid != 0)	{
					Key<Project> projectKey = Key.create(Project.class, projectid);
					logger.info("Created project Key:: " + projectKey);
					issue = ObjectifyService.ofy().load().key(Key.create(projectKey, Issue.class, id)).now();
					logger.info("Got issue:: " + issue);
				}
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | View Issue Details</title>
	
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
    
    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="/css/font-awesome.min.css" rel="stylesheet">
	
</head>
<body>

<div class="header" style="border-bottom: 1px solid; margin-bottom: 19px;">
	<jsp:include page="CommonHeader.jsp"></jsp:include>
</div>


<div class="container-fluid">
	<div class="row">
		<h2 id="issue-title" class="col-sm-10 text-primary">
			<strong><%= null == issue ? "N/A" : issue.getTitle() %></strong>
		</h2>
	</div>
	
	<div class="row">
		<div class="col-sm-12">
			<div id="issue-status" class="text-danger col-sm-3">
				<i class="fa fa-calendar"></i>
				<span>Status: <%= null == issue ? "N/A" : issue.getStatus() %></span>
			</div>
			
			<div id="issue-deadline" class="text-danger col-sm-3">
				<i class="fa fa-calendar"></i>
				<span>Deadline: <%= null == issue ? "N/A" : issue.getEstimatedResolutionDate() %></span>
			</div>
			
			<div id="issue-priority" class="text-danger col-sm-3">
				<i class="fa fa-exclamation-circle"></i>
				<span>Priority: <%= null == issue ? "N/A" : issue.getPriority() == 1 ? "LOW" : (issue.getPriority() == 2 ? "MEDIUM" : "HIGH") %></span>
			</div>
			
			<div id="issue-assigned-to" class="text-info col-sm-3">
				<i class="fa fa-user"></i>
				<span>Assigned to: <%= null == issue ? "N/A" : issue.getAssignedTo() %></span>
			</div>
		</div>
	</div>
	
	<div id="issue-desc" class="row" style="margin-top: 22px;">
		<div class="col-sm-12">
			<%= null == issue ? "N/A" : issue.getDescription() %>
		</div>
	</div>
	
	<h4 style="margin-top: 30px;">Add a Comment</h4>
	
	<form>
	 	<div id="title-group" class="form-group">
	    	<label for="comment-title">Title</label>
	    	<input class="form-control" id="comment-title" name="comment-title" placeholder="Comment Title" type="text">
	  	</div>
	  
	  	<div id="body-group" class="form-group">
	    	<label for="comment-body">Comment</label>
	    	<textarea class="form-control" id="comment-body"></textarea>
	  	</div>

	  	<button type="button" id="add-comment-button" class="btn btn-primary">Submit</button>
	  		
	  	</div>
	</form>
	
	<% 
	   	if (null != issue) {
			if (useremail.equals(issue.getAssignedTo()) && issue.getStatus().equalsIgnoreCase("OPEN"))	{ 
	%>
		<div class="row" style="margin-top: 22px;">
			<div class="col-sm-12">
				<button id="close" data-projectid="<%= projectid %>" data-issueid="<%= id %>" class="btn btn-danger" type="button">Close This Issue</button>
			</div>
		</div>
	<% 
			}
		} 
	%>
	
	<div class="row" style="margin-top: 22px;">
		<div class="col-sm-12">
			<div class="alert alert-success" id="issue-alert">
				
			</div>
		</div>
	</div>
</div> <!-- .container-fluid -->

<!-- jQuery -->
<script type="text/javascript" src="/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

<!-- Custom JavaScript -->
<script type="text/javascript" src="/js/viewissue.js"></script>

</body>
</html>
<%
			}
		}
	} catch (Exception e)	{
	    logger.warning("Exception on page viewissue.jsp. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
		for (StackTraceElement elem : e.getStackTrace())	{
			logger.warning(elem.toString());
		}
		e.printStackTrace();
	}
%>
