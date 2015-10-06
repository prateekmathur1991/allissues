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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allissues.data.Developer;
import com.allissues.data.Project;
import com.googlecode.objectify.Key;

/**
 * Servlet implementation class CreateProject
 */
public class CreateProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CreateProject.class.getName());

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
			logger.warning("Exception while getting session variables");
			e.printStackTrace();
		}
		
		if ("".equals(username) || "".equals(usertype) || "".equals(useremail)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else if ("customer".equalsIgnoreCase(usertype)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		} else {
			String name = request.getParameter("name") == null ? "" : request.getParameter("name");
			String description = request.getParameter("description") == null ? "" : request.getParameter("description");
			
			logger.info("name:: " + name);
			
			if (!"".equals(name) && !"".equals(description))	{
				logger.info("Current useremail:: " + useremail);
				
				Key<Developer> owner = Key.create(Developer.class, useremail);
				logger.info("Created owner key:: " + owner.toString());
				
				Developer developer = ofy().load().key(owner).now();
				if (null != developer) {
					logger.info("Got developer name:: " + developer.getName() + " Developer email:: " + developer.getEmail());
				} else {
					logger.warning("developer null");
				}
				
				Project project = new Project(name, description, owner);
				Key<Project> projectKey = ofy().save().entity(project).now();
				logger.info("Saved project and got key:: " + projectKey.toString());
				
				if (null != developer)	{
					logger.info("Assigning project to developer");
					developer.assignProject(projectKey);
					ofy().save().entity(developer).now();
					
					response.sendRedirect("/Home.jsp");
				} else {
					logger.warning("Developer null");
				}
			}
		}
	}
}
