<%
	session.removeAttribute("username");
	session.removeAttribute("usertype");
	session.removeAttribute("useremail");
	response.sendRedirect("/index.html");
%>
