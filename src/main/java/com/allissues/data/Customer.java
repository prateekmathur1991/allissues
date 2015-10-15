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

package com.allissues.data;

import java.util.ArrayList;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Represents the profile of a Customer, and contains fields to identify the same.
 * 
 * @author Prateek Mathur
 *
 */

@Entity
public class Customer {

    /**
     * Email of the customer, used to uniquely identify the entity in datastore
     */
    @Id private String email;
	
	/**
	 * Name of the customer
	 */
	private String name;

    /**
	 * Represents the customer password
	 */
	private String password;
	
	/**
	 * No. of bugs created by this customer
	 */
	private int noOfBugsCreated;
	
	/**
	 * List of all projects this customer has been added to
	 */
	private ArrayList<Key<Project>> allProjects = new ArrayList<Key<Project>>(0);

	/**
	 * Constructor for the Customer class
	 */
	public Customer(String email, String name, String password)	{
		this.email = email;
		this.name = name;
		this.password = password;

		noOfBugsCreated = 0;
	}
	
	/**
	 * Add a project to this customer
	 */
	public void addProject(Key<Project> projectKey)	{
		this.allProjects.add(projectKey);
	}

    /**
     * Getter for Email
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for Name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for Password
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for noOfBugsCreated
     *
     * @return
     */
    public int getNoOfBugsCreated() {
        return noOfBugsCreated;
    }
    
    /**
     * Getter for allProjects
     *
     * @return
     */
    public List<Key<Project>> getAllProjects() {
	return Collections.unmodifiableList(allProjects);
    }
    
    /**
     * Updates the display name and login password
     */
    public void update(String name, String password) {
		if (null != name) {
		    this.name = name;
	    }

		if (null != password) {
		    this.password = password;
		}
    }
    
    // Making the default constructor private
    private Customer() {}
}
