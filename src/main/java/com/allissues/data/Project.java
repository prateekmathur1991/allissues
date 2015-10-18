
/*  Copyright 2015 Prateek Mathur
    
    All Issues is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    All Issues is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with All Issues. If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * Represents a project, and contains fields to identify the same.
 * 
 * @author Prateek Mathur
 *
 */
package com.allissues.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Project {
	@Id
	/**
	 *  An ID that can uniquely identify the project in the system
	 */
	private Long projectId;
	
	/**
	 * Name of the project
	 */
	private String name;
	
	/**
	 * Short Description of the project
	 */
	private Text description;
	
	/**
	 * Key of the developer object who owns the project
	 */
	@Parent
	private Key<Developer> owner;
	
	/**
	 * List of all developers working on the project
	 */
	private ArrayList<String> allDevelopers = new ArrayList<String>(0);
	
	/**
	 * List of all customers added to the project
	 */
	private ArrayList<String> allCustomers = new ArrayList<String>(0);
			
	/**
	 * No. of bugs filed for this project
	 */
	private int noOfBugs;
	
	/**
	 * Constructor for the Project class, used to create a project and save it in the datastore
	 */
	public Project(String name, String description, Key<Developer> owner)	{
		this.name = name;
		this.description = new Text(description);
		this.owner = owner;
		this.noOfBugs = 0;
	}
	
	/**
	 * Adds a developer to this project by accepting a String
	 * representation of a developer key and adding to the list of
	 * developers.
	 */
	public boolean addDeveloper(String developerKey)	{
		return this.allDevelopers.add(developerKey);
	}
	
	/**
	 * Add a customer to this project by accepting a String representation
	 * of the customer key and adding it to the list of customers.
	 */
	public boolean addCustomer(String customerKey)	{
		return this.allCustomers.add(customerKey);
	}
	
	/**
	 * Removes a developer from this project
	 */
	public boolean removeDeveloper(String developerKey)	{
		return this.allDevelopers.remove(developerKey);
	}
	
	/**
	 * Removes a customer from this project
	 */
	public boolean removeCustomer(String customerKey)	{
		return this.allCustomers.remove(customerKey);
	}
	
	/**
	 * Getter for name
	 */
	public String getName()	{
		return name;
	}
	
	/**
	 * Getter for description
	 */
	public String getDescription()	{
		return description.getValue();
	}
	
	/**
	 * Getter for owner
	 */
	public Key<Developer> getOwner()	{
		return this.owner;
	}
	
	/**
	 * Returns an immutable list of keys of all developer entities
	 * who are working on this project
	 */
	public List<String> getAllDevelopers()	{
		return Collections.unmodifiableList(allDevelopers);
	}
	
	/**
	 * Returns an immutable list of keys of all customer entities
	 * who are working on this project
	 */
	public List<String> getAllCustomers()	{
		return Collections.unmodifiableList(allCustomers);
	}
	
	/**
	 * Updates name and/or description
	 */
	public void update(String name, String description)	{
		if (null != name)	{
			this.name = name;
		}
		
		if (null != description)	{
			this.description = new Text(description);
		}
	}
	
	// Making the default constructor private
	private Project() {}
}
