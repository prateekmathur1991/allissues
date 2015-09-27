<%@page import="java.util.logging.Logger"%>
<%!
	Logger logger = Logger.getLogger("CommonHeader.jsp");
%>
<%
	String username = null, usertype = null;

	try	{
		username = (String) session.getAttribute("username");
		usertype = (String) session.getAttribute("usertype");
	} catch (Exception e)	{
		logger.warning("Exception in CommonHeader.jsp while retrieving session variables");
		e.printStackTrace();
	}
	
	if (null == username || "".equals(username) || null == usertype || "".equals(usertype))	{
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	} else {
%>
<nav class="navbar navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapsible-navbar" aria-expanded="false">
                <span class="sr-only">Toggle Navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/Home.jsp">
                <img src="/img/logo.png"></img>
            </a>
        </div>
        
        <ul class="nav navbar-nav navbar-right">
	        <li class="dropdown">
	            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
	                Welcome, <%= username %><span class="caret"></span>
	            </a>
	            <ul class="dropdown-menu">
	                <li>
	                    <a href="/settings.jsp">Profile Settings</a>
	                </li>
	                <li role="separator" class="divider"></li>
	                <li>
	                    <a href="/logout.jsp">Logout</a>
	                </li>
	            </ul>
	        </li>
        </ul>
    </div>
</nav>
<%
	}
%>
