<%@ page import="java.util.logging.Logger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
	Logger logger = Logger.getLogger("createissue.jsp");    
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
		if (null == username || "".equals(username) || null == usertype || "".equals(usertype))	{
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else	{
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>All Issues | Create an Issue</title>
	
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

<div class="col-sm-12">	
	<h1 class="text-center" style="margin-bottom: 25px;">Enter Issue Details</h1>
    <form class="form-horizontal" id="issue-details" name="issue-details">
        <div class="form-group" id="title-group">
            <label for="title" class="col-sm-2 control-label">Issue Title</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" name="title" placeholder="Title" />
            </div>
        </div>

        <div class="form-group" id="description-group">
            <label for="description" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10">
                <textarea rows="10" class="form-control" id="description" name="description"></textarea>
            </div>
        </div>

        <div class="form-group" id="priority-group">
            <label for="priority" class="col-sm-2 control-label">Priority</label>
            <div class="col-sm-10">
                <select class="form-control" id="priority">
                	<option value="1">Low</option>
                	<option value="2">Medium</option>
                	<option value="3">High</option>
                </select>
            </div>
        </div>

        <div class="form-group" id="assigned-to-group">
            <label for="assigned-to" class="col-sm-2 control-label">Assigned To</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="assigned-to" name="assigned-to" />
            </div>
        </div>
        
        <div class="form-group" id="res-date-group">
        	<label for="res-date" class="col-sm-2 control-label">Resolution Date</label>
        	<div class="col-sm-10">
        		<input type="text" class="form-control" id="res-date" name="res-date" />
        	</div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-success" id="create-issue-button" name="create-issue-button" value="Submit Issue">Add Issue</button>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <div class="alert alert-danger fade in" style="display: none;" id="error"></div>
            </div>
        </div>
    </form>
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
		logger.warning("Exception on page createissue.jsp");
		e.printStackTrace();
	}
%>