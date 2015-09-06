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
		if (null == username || "".equals(username) || null == usertype || "".equals(usertype) || null == useremail || "".equals(useremail))	{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else	{
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | View Issue Details</title>
	
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

<div class="row">
	<h1 class="col-sm-12 text-primary"><strong>Issue Title Appears Here</strong></h1>
</div>

<div class="row">
		<span class="text-danger" style="margin-right: 10px;">
			<i class="fa fa-calendar"></i>
			<span>Status: OPEN</span>
		</span>
		<span class="text-danger" style="margin-right: 10px;">
			<i class="fa fa-calendar"></i>
			<span>Deadline: 09/08/2015</span>
		</span>
		<span class="text-info">
			<i class="fa fa-user"></i>
			<span>Assigned to: Prateek Mathur</span>
		</span>
</div>

<!-- jQuery -->
<script type="text/javascript" src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>

</body>
</html>
<%
		}
	} catch (Exception e)	{
		logger.warning("Exception on page viewissue.jsp");
		e.printStackTrace();
	}
%>