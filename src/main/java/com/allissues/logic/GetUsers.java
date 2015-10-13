package com.allissues.logic;

import static com.allissues.service.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
 * Servlet implementation class GetUsers
 * 
 * Used as a Server Side resource for MagicSuggest JS plugin,
 * which returns a JSON Array of all user display names and email IDs
 * 
 * @author Prateek Mathur
 */
public class GetUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GetUsers.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside GetUsers doGet. Setting response type to application/json");
		response.setContentType("application/json");	

		String username = null, usertype = null, useremail = null;
		HttpSession session = request.getSession();

		try	{
			username = session.getAttribute("username") == null ? "" : (String) session.getAttribute("username");
			usertype = session.getAttribute("usertype") == null ? "" : (String) session.getAttribute("usertype");
			useremail = session.getAttribute("useremail") == null ? "" : (String) session.getAttribute("useremail");
		} catch (Exception e)	{
			logger.warning("Exception on page settings.jsp while retrieving session variables");
			e.printStackTrace();
		}
		
		PrintWriter pout = response.getWriter();
		
		try	{
			if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				JsonObjectBuilder objBuilder = Json.createObjectBuilder();
				JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
				JsonObject responseObj = null;
				
				String query = request.getParameter("query") == null ? "" : request.getParameter("query");
				logger.info("Query:: " + query);
				
				List<Developer> developers = ofy().load().type(Developer.class).list();
				List<Customer> customers = ofy().load().type(Customer.class).list();

				for (Developer developer : developers)	{
					arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Developer").add("name", developer.getName()).add("email", developer.getEmail()).build());
				}
				
				for (Customer customer : customers)	{
					arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Customer").add("name", customer.getName()).add("email", customer.getEmail()).build());
				}
				
				responseObj = objBuilder.add("results", arrBuilder).build();
				String responseStr = responseObj.toString();
				logger.info("responseObj constructed as:: " + responseStr);
				
				pout.println(responseStr);
				pout.flush();
			}
		} catch (Exception e)	{
			logger.warning("Exception on page users.jsp");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
