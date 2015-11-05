/*  Copyright 2015 Prateek Mathur
    
    All Issues is free software: you can redistribute it and/or modify
    it under the terms of the GNU General private License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    All Issues is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General private License for more details.

    You should have received a copy of the GNU General private License
    along with All Issues. If not, see <http://www.gnu.org/licenses/>.
 */

package com.allissues.logic;

import static com.allissues.service.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allissues.data.Customer;
import com.allissues.data.Developer;
import com.googlecode.objectify.Key;

/**
 * Servlet implementation class ProfileActions
 */
public class ProfileActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProfileActions.class.getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileActions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		if ("".equals(username) || "".equals(usertype) || "".equals(useremail)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			response.setContentType("application/json");
			
			JsonObjectBuilder objBuilder = Json.createObjectBuilder();
			JsonObject responseObject = null;
			
			PrintWriter pout = response.getWriter();
			
			try {
				String action = request.getParameter("action") == null ? "" : request.getParameter("action");
				String name = request.getParameter("name") == null ? "" : request.getParameter("name");
				String oldpass = request.getParameter("oldpass") == null ? "" : request.getParameter("oldpass");
				String newpass = request.getParameter("newpass") == null ? "" : request.getParameter("newpass");
				
				logger.info("action:: " + action);
				
				if ("developer".equalsIgnoreCase(usertype)) {
					Developer developer = ofy().load().key(Key.create(Developer.class, useremail)).now();
					if (null != developer) {
						if ("updatepass".equalsIgnoreCase(action)) {
							if (!oldpass.equals(developer.getPassword())) {
								logger.warning("Previous developer password does not match");
								responseObject = objBuilder.add("status", "failure").add("reason", "oldnomatch").build();
								
								pout.println(responseObject.toString());
								pout.flush();
							} else {
								logger.info("Old passwords match. Updating entity now");
								developer.update(null, newpass);
								
								ofy().save().entity(developer).now();
								
								responseObject = objBuilder.add("status", "success").build();
								pout.println(responseObject.toString());
								pout.flush();
							}
						} else if ("updatename".equalsIgnoreCase(action)) {
							developer.update(name, null);
							
							ofy().save().entity(developer).now();
							
							responseObject = objBuilder.add("status", "success").build();
							pout.println(responseObject.toString());
							pout.flush();
						}
					}
				} else if ("customer".equalsIgnoreCase(usertype)) {
					Customer customer = ofy().load().key(Key.create(Customer.class, useremail)).now();
					if (null != customer) {
						if ("updatepass".equalsIgnoreCase(action)) {
							if (!oldpass.equals(customer.getPassword())) {
								logger.warning("Previous customer password does not match");
								responseObject = objBuilder.add("status", "failure").add("reason", "oldnomatch").build();
								
								pout.println(responseObject.toString());
								pout.flush();
							} else {
								logger.info("Old passwords match. Updating entity now");
								customer.update(null, newpass);
								
								ofy().save().entity(customer).now();
								
								responseObject = objBuilder.add("status", "success").build();
								pout.println(responseObject.toString());
								pout.flush();
							}
						} else if ("updatename".equalsIgnoreCase(action)) {
							customer.update(name, null);
							
							ofy().save().entity(customer).now();
							
							responseObject = objBuilder.add("status", "success").build();
							pout.println(responseObject.toString());
							pout.flush();
						}
					}
				}
			} catch (Exception e) {
			    	logger.warning("Exception in ProfileActions Servlet doPost method. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
				for (StackTraceElement elem : e.getStackTrace()) {
logger.warning(elem.toString());
				}
			    	e.printStackTrace();
			} finally	{
				try	{
					if (null != pout)	{
						pout.close();
						pout = null;
					}
				} catch (Exception e)	{
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
