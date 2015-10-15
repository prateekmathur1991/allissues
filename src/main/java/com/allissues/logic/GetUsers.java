package com.allissues.logic;

import static com.allissues.service.OfyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allissues.data.Customer;
import com.allissues.data.Developer;
import com.allissues.data.Project;
import com.googlecode.objectify.Key;

/**
 * Servlet implementation class GetUsers
 * 
 * Used as a Server Side resource for MagicSuggest JS plugin,
 * which returns a JSON Array of all user display names and email IDs.
 * 
 * Also used to add selected people to the current developer's project
 * 
 * @author Prateek Mathur
 */
public class GetUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GetUsers.class.getName());
	
	List<Developer> developers;
	List<Customer> customers;
	
	JsonObjectBuilder objBuilder;
	JsonArrayBuilder arrBuilder;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsers() {
        super();
        
        developers = ofy().load().type(Developer.class).list();
    	customers = ofy().load().type(Customer.class).list();
    	
    	objBuilder = Json.createObjectBuilder();
    	arrBuilder = Json.createArrayBuilder();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				String query = request.getParameter("query") == null ? "" : request.getParameter("query").toLowerCase();
				logger.info("Query:: " + query);
				
				if (!"".equals(query))	{
					for (Developer developer : developers)	{
						if (developer.getName().toLowerCase().contains(query) || developer.getEmail().toLowerCase().contains(query))	{
							arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Developer").add("name", developer.getName()).add("email", developer.getEmail()).build());
						}
					}
					
					for (Customer customer : customers)	{
						if (customer.getName().toLowerCase().contains(query) || customer.getEmail().toLowerCase().contains(query))	{
							arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Customer").add("name", customer.getName()).add("email", customer.getEmail()).build());
						}
					}
					
					pout.println(objBuilder.add("results", arrBuilder).build().toString());
					pout.flush();
				}
			}
			
		} catch (Exception e)	{
			logger.warning("Exception in Servlet GetUsers. doGet() method");
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
		
		JsonReader reader = null;
		PrintWriter pout = response.getWriter();
		boolean error = false;
		try	{
        		if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
        			response.sendError(HttpServletResponse.SC_NOT_FOUND);
        		} else {
        		    	response.setContentType("application/json");
        		    	JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        		    	
        			String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        			logger.info("action:: " + action);
        			
        			if ("addusers".equalsIgnoreCase(action))	{
        				String users = request.getParameter("users") == null ? "" : request.getParameter("users");
        				logger.info(users);
        				
        				Developer developer = ofy().load().key(Key.create(Developer.class, useremail)).now();
        				Project project = null;
        				Key<Project> projKey = null;
        				if (null != developer)	{
        					projKey = developer.getProject();
        					if (null != projKey)	{
        						project = ofy().load().key(projKey).now();
        						logger.info("Got project:: " + project);
        					} else {
        						logger.warning("project Key found null");
        					}
        					
        				} else {
        					logger.warning("developer found null");
        				}
        				
        				reader = Json.createReader(new StringReader(users));
        				
        				JsonStructure structure = reader.read();
        				if (structure.getValueType().equals(JsonValue.ValueType.ARRAY))	{
        					JsonArray usersArr = (JsonArray) structure;
        					Iterator<JsonValue> arrItr = usersArr.iterator();
        					
        					while (arrItr.hasNext())	{
        						JsonValue value = arrItr.next();
        						
        						if (value.getValueType().equals(JsonValue.ValueType.OBJECT))	{
        							JsonObject obj = usersArr.getJsonObject(usersArr.indexOf(value));
        							String type = obj.getString("usertype", "").toLowerCase();
        							String name = obj.getString("name", "");
        							String email = obj.getString("email", "");
        							
        							logger.info("type:: " + type + " name:: " + name + " email:: " + email);
        							
        							if ("developer".equals(type))	{
        							    	Key<Developer> devKey = Key.create(Developer.class, email);
        							    	
        							    	List<Key<Developer>> allDevelopers = project.getAllDevelopers();
        							    	if (allDevelopers.contains(devKey))	{
        							    	    error = true;
        							    	    pout.println(objBuilder.add("status", "failure").add("message", "devExists").add("name", name).build().toString());
        							    	    pout.flush();
        							    	} else {
        							    	    error = false;
        							    	    project.addDeveloper(devKey);
                							    	
        							    	    developer = ofy().load().key(devKey).now();
                							    logger.info("Got developer:: " + developer.getName() + " Adding project now");
                							    developer.addProject(projKey);
                								
                							    logger.info("Saving project and developer entities now");
                							    ofy().save().entities(project, developer).now();
        							    	}
        							} else if ("customer".equals(type))	{
        								Key<Customer> custKey = Key.create(Customer.class, email);
        							    	
        								List<Key<Customer>> allCustomers = project.getAllCustomers();
        								if (allCustomers.contains(custKey))	{
        								    error = true;
        								    pout.println(objBuilder.add("status", "failure").add("message", "custExists").add("name", name).build().toString());
        								    pout.flush();
        								} else {
        								    error = false;
        								    project.addCustomer(Key.create(Customer.class, email));
                							    
                							    Customer customer = ofy().load().key(custKey).now();
                							    logger.info("Got customer " + customer.getName() + " Adding project now");
                							    customer.addProject(projKey);
                							    
                							    logger.info("Saving project and customer entities now");
                							    ofy().save().entities(project, customer).now();
        								}
        							}
        							
        							if (!error)	{
        							    pout.println(objBuilder.add("status", "success").build().toString());
        							    pout.flush();
        							}
        							
        						} else if (value.getValueType().equals(JsonValue.ValueType.ARRAY))	{
        							logger.info("Got object inside JSON Array");
        						}
        					}
        				} else if (structure.getValueType().equals(JsonValue.ValueType.OBJECT))	{
        					logger.info("Got object as a structure type");
        				} 
        			}
        		}
		} catch (Exception e)	{
		    logger.warning("Exception in GetUsers Servlet doPost method");
		    e.printStackTrace();
		    
		    pout.println(objBuilder.add("status", "failure").add("message", e.getClass().getMessage()).build().toString());
		    pout.flush();
		} finally	{
		    try	{
			if (null != reader)	{
			    reader.close();
			    reader = null;
			}
		    } catch (Exception e)	{
			e.printStackTrace();
		    }
		    
		    try {
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
