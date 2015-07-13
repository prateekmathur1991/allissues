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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		String accountType = request.getParameter("userType");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		
		if (accountType.equalsIgnoreCase("Developer"))	{
			Developer developer = new Developer(email, name, password);
			ofy().save().entity(developer).now();
		}
		else if (accountType.equalsIgnoreCase("Customer"))	{
			Customer customer = new Customer(email, name, password);
			ofy().save().entity(customer).now();
		}
		
		// Set the username for session tracking
		request.setAttribute("username", name);

		request.getRequestDispatcher("Home.jsp").forward(request, response);
	}

}
