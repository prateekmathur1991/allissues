package com.allissues.logic;

import static com.allissues.service.OfyService.factory;
import static com.allissues.service.OfyService.ofy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allissues.data.Customer;
import com.allissues.data.Developer;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * The doPost method handles the user request when it comes to the server
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accountType = request.getParameter("type");
		String email = request.getParameter("userEmail");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		
		System.out.println(accountType);
		
		if (accountType == "Developer")	{
			Developer developer = new Developer(email, firstName, lastName, password);
			ofy().save().entity(developer).now();
		}
		else	{
			Customer customer = new Customer(email, firstName, lastName, password);
			ofy().save().entity(customer).now();
		}
		
		response.sendRedirect("Home.jsp");
	}

}
