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
	static Logger logger = Logger.getLogger("settings.jsp");
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
	
	try {
		if (null == username || "".equals(username) || null == usertype || "".equals(usertype) || null == useremail || "".equals(useremail)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			String displayName = "";
			Objectify ofy = ObjectifyService.ofy();
			if ("developer".equals(usertype.toLowerCase())) {
				displayName = ofy.load().key(Key.create(Developer.class, useremail)).now() == null ? "" : ofy.load().key(Key.create(Developer.class, useremail)).now().getName();
			} else {
				displayName = ofy.load().key(Key.create(Customer.class, useremail)).now() == null ? "" : ofy.load().key(Key.create(Customer.class, useremail)).now().getName();
			}
			
			logger.info("Got displayName:: " + displayName);
%>
<!Doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Profile Settings</title>
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
	<form class="form-horizontal" id="settings-form" name="settings-form" method="post">
		<div class="form-group">
  			<h4 class="text-info col-sm-12 col-sm-offset-2">Update Display Name</h4>
  		</div>
		
		<div id="display-name-group">	
			<div class="form-group" id="name-group">
	            <label for="name" class="col-sm-2 control-label" style="text-align: left;">Display Name</label>
	            <div class="col-sm-10">
	                <input type="text" class="form-control" id="name" name="name" value="<%= displayName %>" placeholder="Display Name" />
	            </div>
	        </div>
	        
	        <div class="form-group">
	            <div class="col-sm-10 col-sm-offset-2">
	                <button type="button" class="btn btn-success" id="save-name-button" name="save-name-button">Update</button>
	            </div>
	        </div>
	    </div>
        
        <div id="password-group">
	        <div class="form-group">
	  			<h4 class="text-info col-sm-12 col-sm-offset-2">Update Password</h4>
	  		</div>
	
	        <div class="form-group" id="old-pass-group">
	            <label for="oldpass" class="col-sm-2 control-label" style="text-align: left;">Old Password</label>
	            <div class="col-sm-10">
	                <input type="password" class="form-control" id="oldpass" name="oldpass" placeholder="Enter old password" />
	            </div>
	        </div>
	        
	        <div class="form-group" id="new-pass-group">
	            <label for="newpass" class="col-sm-2 control-label" style="text-align: left;">New Password</label>
	            <div class="col-sm-10">
	                <input type="password" class="form-control" id="newpass" name="newpass" placeholder="Enter new password" />
	            </div>
	        </div>
	        
	        <div class="form-group" id="conf-pass-group">
	            <label for="confpass" class="col-sm-2 control-label" style="text-align: left;">Confirm New Password</label>
	            <div class="col-sm-10">
	                <input type="password" class="form-control" id="confpass" name="confpass" placeholder="Renter new password"/>
	            </div>
	        </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <button type="button" class="btn btn-success" id="save-password-button" name="save-password-button">Update</button>
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
<script type="text/javascript" src="js/settings.js"></script>
</body>
</html>
<% 
		}
	} catch (Exception e) {
		logger.warning("Exception on page settings.jsp");
		e.printStackTrace();
	}
%>