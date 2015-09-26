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
		
		<div class="form-group" id="name-group">
            <label for="name" class="col-sm-2 control-label" style="text-align: left;">Display Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" placeholder="Display Name" />
            </div>
        </div>
        
        <div class="form-group">
  			<h4 class="text-info col-sm-12 col-sm-offset-2">Update Password</h4>
  		</div>

        <div class="form-group" id="old-pass-group">
            <label for="oldpass" class="col-sm-2 control-label" style="text-align: left;">Old Password</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="oldpass" name="oldpass" placeholder="Enter old password" />
            </div>
        </div>
        
        <div class="form-group" id="new-pass-group">
            <label for="newpass" class="col-sm-2 control-label" style="text-align: left;">New Password</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="newpass" name="newpass" placeholder="Enter new password" />
            </div>
        </div>
        
        <div class="form-group" id="conf-pass-group">
            <label for="confpass" class="col-sm-2 control-label" style="text-align: left;">Confirm New Password</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="confpass" name="confpass" placeholder="Renter new password"/>
            </div>
        </div>
        
        <div class="form-group">
            <div class="col-sm-offset-6 col-sm-10">
                <button type="button" class="btn btn-success" id="save-profile-button" name="save-profile-button">Update</button>
            </div>
        </div>
	</form>
</div> <!-- .container-fluid -->

<!-- jQuery -->
<script src="js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
<% 
		}
	} catch (Exception e) {
		logger.warning("Exception on page settings.jsp");
		e.printStackTrace();
	}
%>