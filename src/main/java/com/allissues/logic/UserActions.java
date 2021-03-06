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
 * Servlet implementation class UserActions
 * 
 * Used as a Server Side resource for MagicSuggest JS plugin,
 * which returns a JSON Array of all user display names and email IDs.
 * 
 * Also used to add selected people to the current developer's project
 * 
 * @author Prateek Mathur
 */
public class UserActions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserActions.class.getName());
	
	List<Key<Developer>> developers;
	List<Key<Customer>> customers;
	
	JsonObjectBuilder objBuilder;
	JsonArrayBuilder arrBuilder;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserActions() {
        super();
        
        developers = ofy().load().type(Developer.class).keys().list();
    	customers = ofy().load().type(Customer.class).keys().list();
    	
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
			logger.warning("Exception while getting session variables. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
			for (StackTraceElement elem : e.getStackTrace())	{
logger.warning(elem.toString());
			}
			e.printStackTrace();
		}
		
		PrintWriter pout = response.getWriter();
		
		try	{
			if ("".equals(username) || "".equals(usertype) || "".equals(useremail))	{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				String query = request.getParameter("query") == null ? "" : request.getParameter("query").toLowerCase();
				String action = request.getParameter("action") == null ? "" : request.getParameter("action").toLowerCase();
				
				logger.info("Query:: " + query + " action:: " + action);
				
				Developer developer = ofy().load().key(Key.create(Developer.class, useremail)).now();
				Project project = ofy().load().key(developer.getProject()).now();
				
				if ("getpeople".equals(action))	{
				    List<String> allDevelopers = project.getAllDevelopers();
				    List<String> allCustomers = project.getAllCustomers();
				    
				    for (String devKey : allDevelopers)	{
				    	developer = (Developer) ofy().load().key(Key.create(devKey)).now();
				    	arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Developer").add("name", developer.getName()).add("email", developer.getEmail()).build());
				    }
				    
				    for (String custKey : allCustomers)	{
				    	Customer customer = (Customer) ofy().load().key(Key.create(custKey)).now();
				    	arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Customer").add("name", customer.getName()).add("email", customer.getEmail()).build());
				    }
				    
				    pout.println(arrBuilder.build().toString());
				    pout.flush();
				    
				} else	{
				    if (!"".equals(query))	{
        				for (Key<Developer> devKey : developers)	{
        				    if (devKey.getString().equals(Key.create(Developer.class, useremail).getString()) || project.getAllDevelopers().contains(devKey.getString()))	{
        				    	continue;
        				    } else {
	        					developer = ofy().load().key(devKey).now();
	        					if (developer.getName().toLowerCase().contains(query) || developer.getEmail().toLowerCase().contains(query))	{
	        					    arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Developer").add("name", developer.getName()).add("email", developer.getEmail()).build());
	        					}
        				    }
        				}
        					
        				for (Key<Customer> custKey : customers)	{
        				    if (custKey.getString().equals(Key.create(Customer.class, useremail).getString()) || project.getAllCustomers().contains(custKey.getString()))	{
        				    	continue;
        				    } else {	
	        				    Customer customer = ofy().load().key(custKey).now();
	        					if (customer.getName().toLowerCase().contains(query) || customer.getEmail().toLowerCase().contains(query))	{
	        					    arrBuilder = arrBuilder.add(objBuilder.add("usertype", "Customer").add("name", customer.getName()).add("email", customer.getEmail()).build());
	        					}
        				    }
        				}
        					
        				pout.println(objBuilder.add("results", arrBuilder).build().toString());
        				pout.flush();
				    }
				}
			}
			
		} catch (Exception e)	{
			logger.warning("Exception in UserActions Servlet doGet method. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
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
			logger.warning("Exception while retrieving session variables. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
			for (StackTraceElement elem : e.getStackTrace()) {
logger.warning(elem.toString());
			}
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
							    	
							    	if (project.getAllDevelopers().contains(devKey.getString()))	{
							    	    error = true;
							    	    pout.println(objBuilder.add("status", "failure").add("message", "devExists").add("name", name).build().toString());
							    	    pout.flush();
							    	} else {
							    	    error = false;
							    	    project.addDeveloper(devKey.getString());
        							    	
							    	    developer = ofy().load().key(devKey).now();
        							    logger.info("Got developer:: " + developer.getName() + " Adding project now");
        							    developer.addProject(projKey.getString());
        								
        							    logger.info("Saving project and developer entities now");
        							    ofy().save().entities(project, developer).now();
							    	}
    							} else if ("customer".equals(type))	{
    								Key<Customer> custKey = Key.create(Customer.class, email);
    							    	
    								if (project.getAllCustomers().contains(custKey.getString()))	{
    								    error = true;
    								    pout.println(objBuilder.add("status", "failure").add("message", "custExists").add("name", name).build().toString());
    								    pout.flush();
    								} else {
    								    error = false;
    								    project.addCustomer(Key.create(Customer.class, email).getString());
            							    
            							Customer customer = ofy().load().key(custKey).now();
            							logger.info("Got customer " + customer.getName() + " Adding project now");
            							customer.addProject(projKey.getString());
            							    
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
    			} else if ("removeuser".equals(action))	{
    				String name = request.getParameter("name") == null ? "" : request.getParameter("name");
    				String email = request.getParameter("email") == null ? "" : request.getParameter("email");
    				String type = request.getParameter("type") == null ? "" : request.getParameter("type").toLowerCase();
    				
    				logger.info("name:: " + name + " email:: " + email + " type:: " + type);
    				
    				Developer developer = ofy().load().key(Key.create(Developer.class, useremail)).now();
    				Key<Project> projKey = developer.getProject();
    				Project project = ofy().load().key(projKey).now();
    				
    				if ("developer".equals(type))	{
    					Key<Developer> devKey = Key.create(Developer.class, email);
    					
    					if (project.removeDeveloper(devKey.getString()))	{
    						logger.info("developer removed from project");
    					} else {
    						logger.info("developer not removed from project");
    					}
    					
    					developer = ofy().load().key(devKey).now();
    					
    					if (developer.removeProject(projKey.getString()))	{
    						logger.info("project removed from developer");
    					} else {
    						logger.info("project not removed from developer");
    					}
    					
    					ofy().save().entities(project, developer).now();
    					
    				} else if ("customer".equals(type))	{
    					Key<Customer> custKey = Key.create(Customer.class, email);
    					
    					if (project.removeCustomer(custKey.getString()))	{
    						logger.info("customer removed from project");
    					} else {
    						logger.info("customer not removed from project");
    					}
    					
    					Customer customer = ofy().load().key(custKey).now();
    					
    					if (customer.removeProject(projKey.getString()))	{
    						logger.info("project removed from customer");
    					} else {
    						logger.info("project not removed from customer");
    					}
    					
    					ofy().save().entities(project, customer).now();
    				}
    				
    				pout.println(objBuilder.add("status", "success").build().toString());
    				pout.flush();
    			}
    		}
		} catch (Exception e)	{
		    logger.warning("Exception in UserActions Servlet doPost method. Exception class:: " + e.getClass().getName() + " Exception message:: " + e.getLocalizedMessage());
		    for (StackTraceElement elem : e.getStackTrace()) {
logger.warning(elem.toString());
		    }
		    e.printStackTrace();
		    
		    pout.println(objBuilder.add("status", "failure").build().toString());
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
