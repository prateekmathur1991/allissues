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

/**
 * Represents the profile of a Developer, and contains fields to identify the same.
 * 
 * @author Prateek Mathur
 *
 */

package com.allissues.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Developer {

	/**
	 * Email of the customer, used to uniquely identify the entity in datastore
	 */
	@Id private String email;
	
	/**
	 * Name of the developer
	 */
	private String name;
	
	/**
	 * Represents the user password
	 */
	private String password;
	
	/**
	 * No. of bugs fixed by the developer
	 */
	private int noOfBugsFixed;
	
	/**
	 * No. of bugs assigned to the developer
	 */
	private int noOfBugsAssigned;
	
	/**
	 * Key of the project of which this developer is a Project Leader
	 */
	private Key<Project> projectKey;
	
	/**
	 * List of all project this developer is working on
	 */
	private ArrayList<String> allProjects = new ArrayList<String>(0);

	/**
	 * Constructor for the Developer class, used to create a developer entity and save it in the datastore.
	 */
	public Developer(String email, String name,  String password)	{
		this.email = email;
		this.name = name;
		this.password = password;
		this.projectKey = null;

		noOfBugsAssigned = 0;
		noOfBugsFixed = 0;
	}
	
	/**
	 * Assigns a project to this developer, making him/her the owner and Project
	 * Leader for this project
	 */
	public void assignProject(Key<Project> projectKey)	{
		this.projectKey = projectKey;
	}
	
	
	/**
	 * Adds a project to the list of projects this developer is working on
	 */
	public void addProject(String projectKey)	{
		this.allProjects.add(projectKey);
	}

	/**
	 * Getter for email
	 */
	public String getEmail()	{
		return email;
	}

	/**
	 * Getter for name
	 */
	public String getName()	{
		return name;
	}

	/**
	 * Getter for password
	 */
	public String getPassword()	{
		return password;
	}

	/**
	 * Getter for noOfBugsFixed
	 */
	public int getNoOfBugsFixed()	{
		return noOfBugsFixed;
	}

	/**
	 * Getter for noOfBugsAssigned
	 */
	public int getNoOfBugsAssigned()	{
		return noOfBugsAssigned;
	}
	
	/**
	 * Getter for the project this developer ownes
	 */
	public Key<Project> getProject()	{
		return this.projectKey;
	}
	
	/**
	 * Returns an immutable list of all project keys this developer
	 * is working on
	 */
	public List<String> getProjects()	{
		return Collections.unmodifiableList(allProjects);
	}
	
	/**
	 * Updates display name and/or login password
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
	private Developer() {}
}