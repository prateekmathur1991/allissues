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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// Even if a GET request arrives, we will serve it with doPost method.
		doPost(request, response);
	}
       
   /**
	 * doPost implementation for RegisterServlet
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside LoginServlet");
		
		String email = request.getParameter("email") == null ? "" : request.getParameter("email");
		String password = request.getParameter("password") == null ? "" : request.getParameter("password");
		
		logger.info("email:: " + email + " password::" + password);
		
		Customer customer = null;
		Developer developer = null;

		if (!email.equals("") && !password.equals("")) {
			// Try to load Customer entity from datastore
			customer = ofy().load().key(Key.create(Customer.class, email))
					.now();
			if (null == customer) {
				developer = ofy().load()
						.key(Key.create(Developer.class, email)).now();
				if (developer != null) {
					if (developer.getPassword().equals(password)) {
						// Developer login successful
						// Set the username for session tracking
						request.setAttribute("username", developer.getName());
						request.getRequestDispatcher("Home.jsp").forward(
								request, response);
					}
				}
			} else {
				if (customer.getPassword().equals(password)) {
					// Customer login successful
					// Set the username for session tracking
					request.setAttribute("username", customer.getName());
					request.getRequestDispatcher("Home.jsp").forward(request,
							response);
				}
			}
		}
		
		// Login Faliure
		PrintWriter out = response.getWriter();
		out.println("loginfaliure");
		
	}

}
