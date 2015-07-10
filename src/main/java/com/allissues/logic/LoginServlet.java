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
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Load the entity from datastore
		
	}

}
