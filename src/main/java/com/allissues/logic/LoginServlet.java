package com.allissues.logic;

import static com.allissues.service.OfyService.ofy;

import com.allissues.data.Customer;
import com.allissues.data.Developer;
import com.googlecode.objectify.Key;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Customer customer = null;
		Developer developer = null;

		// Try to load Customer entity from datastore
		customer = ofy().load().key(Key.create(Customer.class, email)).now();
		
		if (null == customer)	{
			developer = ofy().load().key(Key.create(Developer.class, email)).now();
			if (developer != null)	{
				if (developer.getPassword().equals(password))	{
					// Developer login successful
					// Set the username for session tracking
					request.setAttribute("username", developer.getName());
					request.getRequestDispatcher("Home.jsp").forward(request, response);
				}
			}
		} else	{
			if (customer.getPassword().equals(password)) {
				// Customer login successful
				// Set the username for session tracking
				request.setAttribute("username", customer.getName());
				request.getRequestDispatcher("Home.jsp").forward(request, response);
			}
		}
		
		// Login Faliure
		PrintWriter out = response.getWriter();
		out.println("loginfaliure");
		
	}

}
