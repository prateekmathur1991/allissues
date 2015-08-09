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
	<div class="col-xs-4">
		<div class="panel panel-primary">
			<div class="panel panel-heading">
				
			</div>
			<div class="panel-body">
				<i class="fa fa-eye fa-2x"></i>
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
		logger.warning("Exception on page Home.jsp");
		e.printStackTrace();
	}
%>