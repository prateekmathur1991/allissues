/*  Copyright 2015 Prateek Mathur
    
    All Issues is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    All Issues is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with All Issues. If not, see <http://www.gnu.org/licenses/>.
 */

package com.allissues.logic;

import static com.allissues.service.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allissues.data.Comment;
import com.allissues.data.Issue;
import com.allissues.data.Project;
import com.googlecode.objectify.Key;

/**
 * Servlet implementation class IssueActions
 * 
 * Creates a new issues and saves it in the datastore.
 * 
 * @author Prateek Mathur
 */
public class IssueActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(IssueActions.class.getName());
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String username = null, usertype = null, useremail = null;
		
		try	{
			username = session.getAttribute("username") == null ? "" : (String) session.getAttribute("username");
			usertype = session.getAttribute("usertype") == null ? "" : (String) session.getAttribute("usertype");
			useremail = session.getAttribute("useremail") == null ? "" : (String) session.getAttribute("useremail");
		} catch (Exception e)	{
		    	logger.warning("Exception while gettting session variables. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
			for (StackTraceElement elem : e.getStackTrace()) {
				logger.warning(elem.toString());
			}
			e.printStackTrace();
		}
		
		try	{
			if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				String action = request.getParameter("action") == null ? "" : request.getParameter("action");
				
				if ("close".equals(action))	{
					PrintWriter pout = null;
					
					try {
						pout = response.getWriter();
						
						long id = request.getParameter("id") == null ? 0 : Long.parseLong(request.getParameter("id"));
						long projectid = request.getParameter("projectid") == null ? 0 : Long.parseLong(request.getParameter("projectid"));
						
						if (id > 0 && projectid > 0)	{
							Key<Project> projectKey = Key.create(Project.class, projectid);
							logger.info("Got project key:: " + projectKey);
							
							Issue issue = null;
							if (null != projectKey)	{
								issue = ofy().load().key(Key.create(projectKey, Issue.class, id)).now();
								logger.info("Got issue:: " + issue);
							} else {
								logger.warning("projectKey null");
								pout.println("closeError");
							}
							
							if (null != issue)	{
								issue.close();
								ofy().save().entity(issue).now();
								pout.println("closeSuccess");
							} else {
								logger.warning("issue null");
								pout.println("closeError");
							}
						}
					} catch (Exception e) {
						logger.warning("Exception in IssueActions Servlet doPost method while closing issue. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
						for (StackTraceElement elem : e.getStackTrace()) {
						    logger.warning(elem.toString());
						}
						pout.println("closeError");
						e.printStackTrace();
					} finally {
						if (null != pout)	{
							pout.close();
							pout = null;
						}
					}
					
				} else if ("addcomment".equalsIgnoreCase(action)) {
					 String commentTitle = request.getParameter("title") == null ? "" : request.getParameter("title");
					 String commentBody = request.getParameter("body") == null ? "" : request.getParameter("body");
					 
					 logger.info("Comment Title:: " + commentTitle + " Comment Body:: " + commentBody);
					 
					 // TODO
					 // Add a line to add Issue Key to Comment Constructor
					 // Add the code to create and save a comment in datastore.
					 Comment comment = new Comment(commentTitle, commentBody, useremail);
					
				} else {
					String title = request.getParameter("title") == null ? "" : request.getParameter("title");
					String description = request.getParameter("description") == null ? "" : request.getParameter("description");
					int priority = request.getParameter("priority") == null ? 0 : Integer.parseInt(request.getParameter("priority"));
					String assignedTo = request.getParameter("assigned-to") == null ? "" : request.getParameter("assigned-to");
					String deadline = request.getParameter("res-date") == null ? "" : request.getParameter("res-date");
					String project = request.getParameter("project") == null ? "" : request.getParameter("project");
					
					logger.info("title:: " + title + " priority:: " + priority + " assignedTo:: " + assignedTo + " projectId:: " + project + " deadline:: " + deadline);
					
					Key<Project> projectKey = Key.create(Project.class, Long.parseLong(project));
					Issue issue = new Issue(projectKey, title, description, priority, useremail, assignedTo, deadline, "developer".equalsIgnoreCase(usertype) == true ? true : false);
					
					Key<Issue> key = ofy().save().entity(issue).now();
					long id = key.getId();
	
					logger.info("Issue saved successfully. Generated ID:: " + id + " Parent project ID:: " + projectKey.getId());
					
					response.sendRedirect("/issue/" + title.toLowerCase().replaceAll(" ", "-") + "-" + id + "-" + projectKey.getId());
					
				}
			}
		} catch (Exception e)	{
			logger.warning("Exception in IssueActions Servlet doPost method. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
			for (StackTraceElement elem : e.getStackTrace()) {
			    logger.warning(elem.toString());
			}
			e.printStackTrace();
		}
		
	}
}