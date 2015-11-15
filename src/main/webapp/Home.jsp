<%@page import="com.allissues.service.OfyService"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.allissues.data.Project"%>
<%@page import="com.googlecode.objectify.Key"%>
<%@page import="com.googlecode.objectify.cmd.QueryKeys"%>
<%@page import="com.googlecode.objectify.cmd.Query"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.allissues.data.Issue"%>
<%@page import="java.util.List"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="com.googlecode.objectify.Objectify"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	Logger logger = Logger.getLogger("Home.jsp");
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
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Home</title>
	<link rel="shortcut icon" type="image/png" href="img/favicon.png" />
    
    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <!--  Font Awesome -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
</head>

<body>

<div class="header" style="border-bottom: 1px solid; margin-bottom: 19px;">
	<jsp:include page="CommonHeader.jsp"></jsp:include>
</div>

<div class="container-fluid">
<%
	List<Key<Issue>> closedIssues = OfyService.ofy().load().type(Issue.class).filter("status", "CLOSED").filter("assignedTo", useremail).keys().list();
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.DATE, 7);
	
	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(new Date());
	cal2.add(Calendar.DATE, -7);
	
	List<Key<Issue>> approachingIssues = OfyService.ofy().load().type(Issue.class).filter("status", "OPEN").filter("assignedTo", useremail).filter("estimatedResolutionDate <=", cal.getTime()).keys().list();
	List<Key<Issue>> submittedIssues = OfyService.ofy().load().type(Issue.class).filter("status", "OPEN").filter("assignedTo", useremail).filter("createdOn >=", cal2.getTime()).keys().list();
%>
	<div class="row">
		<div class="col-lg-4 col-md-6">
			<div class="panel panel-success">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-check-circle fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">	
							<div style="font-size: 40px;"><%= closedIssues.size() %></div>
	                        <div>Issue(s) Closed!</div>
						</div>			
					</div>
				</div>
				<div class="panel-body btn-lg">
					<a href="#">View Details</a>
				</div>
			</div>
		</div>
		
		<div class="col-lg-4 col-md-6">
			<div class="panel panel-danger">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-exclamation-circle fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">	
							<div style="font-size: 40px;"><%= approachingIssues.size() %></div>
							<div>Issue(s) Approaching Deadline</div>
						</div>		
					</div>
				</div>
				<div class="panel-body btn-lg">
					<a href="#">View Details</a>
				</div>
			</div>
		</div>
		
		<div class="col-lg-4 col-md-6">
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-3">
							<i class="fa fa-tasks fa-5x"></i>
						</div>
						<div class="col-xs-9 text-right">	
							<div style="font-size: 40px;"><%= submittedIssues.size() %></div>
	                        <div>New Issue(s) Submitted!</div>
						</div>		
					</div>
				</div>
				<div class="panel-body btn-lg">
					<a href="#">View Details</a>
				</div>
			</div>
		</div>
	</div> <!-- .row -->
</div> <!-- .cotainer-fluid -->

<div class="container-fluid">
	<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<h3 class="col-sm-9"><%= "customer".equalsIgnoreCase(usertype) == true ? "Open Issues Created By Me" : "Open Issues Assigned To Me"%></h3>
			<div class="col-sm-3" style="margin-top: 15px;">
				<a href="/createissue.jsp" class="btn btn-primary pull-right">Add new</a>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover text-center">
				<thead>
					<tr>
						<td>Issue ID</td>
						<td>Title</td>
						<td>Priority</td>
						<td>Created By</td>
						<td>Created On</td>
						<td>Estimated Resolution Date</td>
					</tr>		
				</thead>
				
				<tbody>
				<%
					List<Key<Issue>> openIssues = null;
					
					if ("developer".equalsIgnoreCase(usertype))	{
						logger.info("developer logged in. Loading issues assigned to him");
						openIssues = OfyService.ofy().load().type(Issue.class).filter("status", "OPEN").filter("assignedTo", useremail).keys().list();
					} else if ("customer".equalsIgnoreCase(usertype))	{
						logger.info("customer logged in. Loading issues created by him");
						openIssues = OfyService.ofy().load().type(Issue.class).filter("status", "OPEN").filter("createdBy", useremail).filter("developerIssue", false).keys().list();
					}
					
					if (null != openIssues)	{
						logger.info("openIssues not null");
						if (openIssues.size() > 0)	{
							logger.info("openIssues size zero. Startging issueKey loop");
							for (Key<Issue> issueKey : openIssues)	{
								logger.info("Inside issueKey loop. Loading issue now");
								
								Issue issue = OfyService.ofy().load().key(issueKey).now();
								logger.info("Got issue:: " + issue);
								
								String issueUrl = "";
								if (null != issue && null != issue.getProjectKey())	{
									issueUrl = "/issue/" + issue.getTitle().toLowerCase().replace(" ", "-") + "-" + issueKey.getId() + "-" + issue.getProjectKey().getId();
								}
								logger.info("issueUrl:: " + issueUrl);
				%>
				<tr>
					<% logger.info("printing key"); %>
					<td><%= issueKey.getId() %></td>
					<% logger.info("printing url"); %>
					<td>
						<% if ("".equals(issueUrl)) { %>
							<span><%= null != issue ? issue.getTitle() : "N/A" %></span>
						<% } else { %>
							<a target="_blank" href="<%= issueUrl %>"><%= null != issue ? issue.getTitle() : "N/A" %></a>
						<% } %>
					</td>
					<% logger.info("printing priority"); %>
					<td><%= null != issue ? (issue.getPriority() == 1 ? "LOW" : (issue.getPriority() == 2 ? "MEDIUM" : "HIGH")) : "N/A" %></td>
					<% logger.info("printing createdBy"); %>
					<td><%= null != issue ? issue.getCreatedBy() : "N/A" %></td>
					<% logger.info("printing createdOn"); %>
					<td><%= null != issue ? issue.getCreatedOn() : "N/A" %></td>
					<% logger.info("printing estimatedResDate"); %>
					<td><%= null != issue ? issue.getEstimatedResolutionDate() : "N/A" %></td>
				</tr>
				<%
							}
						} else { logger.warning("openIssues list size is zero");
				%>
					<tr>
						<td colspan="6">No Issues Currently Open!!</td>
					</tr>
				<%
						}
					} else { 
						logger.warning("openIssues list object is null");
					}
				%>
				</tbody>
				
			</table>
		</div> <!-- .table-responsive -->
		</div>
	</div> <!-- .row -->
</div> <!-- .container-fluid -->

<div id="myModal" class="modal fade">
	<div class="modal-dialog">
	    <div class="modal-content">
	    	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        	<h4 class="modal-title">Issue Details</h4>
	      	</div>
	      	
	      	<div class="modal-body">
	        	
	      	</div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
	      	</div>
	    </div>
	</div>
</div>

<!-- jQuery -->
<script src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>
<%
		}
	} catch (Exception e)	{
	    logger.warning("Exception on page Home.jsp. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
		for (StackTraceElement elem : e.getStackTrace())	{
logger.warning(elem.toString());
		}
		e.printStackTrace();
	}
%>
