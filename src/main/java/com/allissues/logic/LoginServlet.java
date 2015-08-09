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
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class.getName());

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
		logger.info("Inside LoginServlet");
		
		response.setContentType("application/json");
		
		String email = request.getParameter("email") == null ? "" : request.getParameter("email");
		String password = request.getParameter("password") == null ? "" : request.getParameter("password");
		
		Customer customer = null;
		Developer developer = null;
		
		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
		JsonObject responseObject = null;
		
		PrintWriter pout = response.getWriter();
		
		HttpSession session = request.getSession();

		if (!email.equals("") && !password.equals("")) {
			boolean loginSuccess = false;
			logger.info("Trying to load customer entity");
			customer = ofy().load().key(Key.create(Customer.class, email)).now();
			
			if (null == customer) {
				logger.info("customer entity not found. Trying to load developer entity");
				developer = ofy().load().key(Key.create(Developer.class, email)).now();
				
				if (developer != null) {
					logger.info("developer entity found");
					if (developer.getPassword().equals(password)) {
						logger.info("Developer login successful");
						loginSuccess = true;
						
						session.setAttribute("username", developer.getName());
						session.setAttribute("usertype", "developer");
						
						responseObject = objBuilder.add("status", "success").add("userType", "developer").add("forwardUrl", "Home.jsp").build();
						
						logger.info("JsonObject constructed as:: " + responseObject);
						pout.println(responseObject.toString());
						pout.flush();
						
						// request.getRequestDispatcher("Home.jsp").forward(request, response);
					}
				}
			} else {
				if (customer.getPassword().equals(password)) {
					logger.info("Customer login successful");
					loginSuccess = true;
					
					session.setAttribute("username", customer.getName());
					session.setAttribute("usertype", "customer");
					
					responseObject = objBuilder.add("status", "success").add("userType", "customer").add("forwardUrl", "Home.jsp").build();
					
					logger.info("JsonObject constructed as:: " + responseObject);
					pout.println(responseObject.toString());
					pout.flush();
					
					// request.getRequestDispatcher("Home.jsp").forward(request, response);
				}
			}
			
			if (!loginSuccess) {
				// Login Failure
				responseObject = objBuilder.add("status", "faliure").build();
				logger.info("Login Faliure. JsonObject constructed as:: " + responseObject);
				
				pout.println(responseObject.toString());
				pout.flush();
			}
			
		}
	}

}
