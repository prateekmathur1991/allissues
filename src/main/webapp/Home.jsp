<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	Logger logger = Logger.getLogger("Home.jsp");
%>
<%
	String username = null, usertype = null;

	try	{
		username = (String) session.getAttribute("username");
		usertype = (String) session.getAttribute("usertype");
	} catch (Exception e)	{
		logger.warning("Exception in Home.jsp while retrieving session variables");
		e.printStackTrace();
	}
	
	try	{

		// The absence of session variables denotes that nobody is logged in
		if (username == null || username.equals("") || usertype == null || usertype.equals(""))	{
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

<div class="row">
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-success">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-check-circle fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">	
						<div style="font-size: 40px;">15</div>
                        <div>Issues Closed!</div>
					</div>			
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-danger">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-exclamation-circle fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">	
						<div style="font-size: 40px;">5</div>
						<div>Issues Open!</div>
					</div>		
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-danger">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-exclamation-circle fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">	
						<div style="font-size: 40px;">10</div>
						<div>Issues Approaching Deadline</div>
					</div>		
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-lg-3 col-md-6">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="row">
					<div class="col-xs-3">
						<i class="fa fa-tasks fa-5x"></i>
					</div>
					<div class="col-xs-9 text-right">	
						<div style="font-size: 40px;">10</div>
                        <div>New Issues Submitted!</div>
					</div>		
				</div>
			</div>
		</div>
	</div>
</div> <!-- .row -->

<div class="row">
<h4>All Open Issues</h4>
	<table class="table table-bordered">
		<thead>
			<tr>
				<td>Issue ID</td>
				<td>Title</td>
				<td>Submission Date</td>
				<td>Project Submitted To</td>
				<td>Created By</td>
				<td>Assigned To</td>
				<td>Deadline</td>		
			</tr>		
		</thead>
		<tbody>
			
		</tbody>
	</table>
</div> <!-- .row -->

<!-- jQuery -->
<script src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>
<%
		}
	} catch (Exception e)	{
		logger.warning("Exception on page Home.jsp");
		e.printStackTrace();
	}
%>
