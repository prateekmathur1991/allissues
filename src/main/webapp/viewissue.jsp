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
		username = (String) session.getAttribute("username");
		usertype = (String) session.getAttribute("usertype");
		useremail = (String) session.getAttribute("useremail");
	} catch (Exception e)	{
		logger.warning("Exception in Home.jsp while retrieving session variables");
		e.printStackTrace();
	}
	
	try	{
		// The absence of session variables denotes that nobody is logged in
		if ((null == username || "".equals(username)) && (null == usertype || "".equals(usertype)) && (null == useremail || "".equals(useremail)))	{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else	{
			if ("customer".equalsIgnoreCase(usertype))	{
				// Customers cannot view a issue created by a developer
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} else {
				long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
				logger.info("Got ID:: " + id);
				
				Objectify ofy = ObjectifyService.ofy();
				
				Key<Issue> issueKey = null;
				Issue issue = null;
				if (id != 0)	{
					issue = ofy.load().key(Key.create(Issue.class, id)).now();
				}
				
				if (null != issue)	{
					logger.info("Title:: " + issue.getTitle() + " Status:: " + issue.getStatus() + " Deadline::" + issue.getEstimatedResolutionDate());
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
		<h1 class="col-sm-12 text-primary"><strong><%= null == issue ? "N/A" : issue.getTitle() %></strong></h1>
	</div>
	
	<div class="row">
		<div class="col-sm-9">
			<div class="text-danger col-sm-3">
				<i class="fa fa-calendar"></i>
				<span>Status: <%= null == issue ? "N/A" : issue.getStatus() %></span>
			</div>
			
			<div class="text-danger col-sm-3 col-sm-offset-1">
				<i class="fa fa-calendar"></i>
				<span>Deadline: <%= null == issue ? "N/A" : issue.getEstimatedResolutionDate() %></span>
			</div>
			
			<div class="text-info col-sm-3 col-sm-offset-1">
				<i class="fa fa-user"></i>
				<span>Assigned to: <%= null == issue ? "N/A" : issue.getAssignedTo() %></span>
			</div>
		</div>
	</div>
	
	<div class="row">
		<p class="text-justify col-sm-12" style="margin-top: 17px;">
			<%= issue.getDescription() %>
		</p>
	</div>
</div> <!-- .container-fluid -->

<!-- jQuery -->
<script type="text/javascript" src="/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="/js/bootstrap.min.js"></script>

</body>
</html>
<%
			}
		}
	} catch (Exception e)	{
		logger.warning("Exception on page viewissue.jsp");
		e.printStackTrace();
	}
%>