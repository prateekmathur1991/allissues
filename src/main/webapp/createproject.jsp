<%@page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	Logger logger = Logger.getLogger("createproject.jsp");
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
		} else if ("customer".equalsIgnoreCase(usertype))	{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		} else	{
			
%>
<!Doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Create Project</title>
	
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
	<div class="row">
		<div class="col-sm-12">
			<h2 class="text-center">Create a Project to Get Started</h2>
			<form action="CreateProject" method="post" class="form-horizontal" id="project-details">
		        <div class="form-group" id="namegroup">
		            <label for="name" class="col-sm-2 control-label">Project Name</label>
		            <div class="col-sm-10">
		                <input type="text" class="form-control" id="name" name="name" placeholder="Name" />
		            </div>
		        </div>
		
		        <div class="form-group" id="descgroup">
		            <label for="email" class="col-sm-2 control-label">Description</label>
		            <div class="col-sm-10">
						<textarea rows="10" class="form-control" id="description" name="description"></textarea>	
		            </div>
		        </div>
		        
		        <div class="form-group">
		            <div class="col-sm-10 col-sm-offset-2">
		                <button type="submit" class="btn btn-success" id="create-project-button" name="create-project-button">Create Project</button>
		            </div>
		        </div>
		        
		        <div class="form-group">
		            <div class="col-sm-offset-2 col-sm-10">
		                <div class="alert alert-danger fade in" style="display: none;" id="error"></div>
		            </div>
		        </div>
		    </form>
	    </div>
	</div>
</div>

<!-- jQuery -->
<script src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="js/bootstrap.min.js"></script>

<!-- TinyMCE -->
<script type="text/javascript" src="tinymce/tinymce.min.js"></script>

<!-- Custom javascript -->
<script src="js/createproject.js"></script>

</body>

</html>
<% 
		}
	} catch (Exception e)	{
	    logger.warning("Exception on page createproject.jsp. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
		for (StackTraceElement elem : e.getStackTrace())	{
logger.warning(elem.toString());
		}
		e.printStackTrace();
	}
%>