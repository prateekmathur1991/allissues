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

import com.allissues.data.Customer;
import com.allissues.data.Developer;
import com.googlecode.objectify.Key;

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

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RegisterServlet.class.getName());

	/**
	 * doGet implementation for RegisterServlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{
		// For security reasons, our servlet will not respond to GET requests at all
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
       
   /**
	 * doPost implementation for RegisterServlet
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		PrintWriter pout = response.getWriter();
		
		String accountType = request.getParameter("userType");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonObject responseObject = null;
		
		boolean errorFlag = false;
		
		// Search for existing developer or customer entities with the supplied email
		if (ofy().load().key(Key.create(Developer.class, email)) != null)	{
			// Existing developer account found
			errorFlag = true;
			responseObject = builder.add("status", "failure").add("error", "developerExists").build();
			
			logger.info("JsonObject constructed as:: " + responseObject);
			pout.println(responseObject.toString());
			pout.flush();
		} else	{
			if (ofy().load().key(Key.create(Customer.class, email)) != null)	{
				// Existing customer account found
				errorFlag = true;
				responseObject = builder.add("status", "failure").add("error", "customerExists").build();
				
				logger.info("JsonObject constructed as:: " + responseObject);
				pout.println(responseObject.toString());
				pout.flush();
			}
		}
		
		if (!errorFlag) {
			if (accountType.equals("Developer")) {
				Developer developer = new Developer(email, name, password);
				ofy().save().entity(developer).now();
			} else if (accountType.equals("Customer")) {
				Customer customer = new Customer(email, name, password);
				ofy().save().entity(customer).now();
			}
			
			// Set the user details in session
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			session.setAttribute("userType", accountType);
			session.setAttribute("name", name);
			
			responseObject = builder.add("status", "success").add("forwardUrl", "Home.jsp").build();
			pout.println(responseObject);
			pout.flush();
		}
	}

}
