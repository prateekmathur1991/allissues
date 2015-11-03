package com.allissues.logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewIssueServlet
 */
public class ViewIssue extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(ViewIssue.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewIssue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length() + 1);
		logger.info("path:: " + path);
		
		String [] parts = path.split("/");
		logger.info("Got parts:: " + Arrays.toString(parts));
		
		if (parts.length == 2)	{
			if ("issue".equalsIgnoreCase(parts[0]))	{
				String [] issuePath = parts[1].split("-");
				
				if (null != issuePath && issuePath.length > 0) {
					logger.info("Got issuePath:: " + Arrays.toString(issuePath));
					String id = issuePath[issuePath.length - 2];
					logger.info("Got ID:: " + id);
					
					String projectid = issuePath[issuePath.length - 1];
					logger.info("Got parent project ID:: " + projectid);
					
					String forwardUrl = "/viewissue.jsp?id=" + id + "&projectid=" + projectid;
					logger.info("forwardUrl:: " + forwardUrl);
					request.getRequestDispatcher(forwardUrl).forward(request, response);
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
