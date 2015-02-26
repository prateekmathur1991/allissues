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
	 * Constructor for the Developer class, used to create a developer entity and save it in the datastore.
	 */
	public Customer(String email, String name, String password)	{
		this.email = email;
		this.name = name;
		this.password = password;
		
		noOfBugsCreated = 0;
		noOfProjects = 0;
	}
	
	@Id // The @Id annotation represents that this field will be used to uniquely identify the entity in the datstore
	/**
	 * Email of the customer
	 */
	private String email;
	
	/**
	 * Name of the customer
	 */
	private String name;
	
	/**
	 * Represents the user password
	 */
	private String password;
	
	/**
	 * No. of bugs created by this customer
	 */
	private int noOfBugsCreated;
	
	/**
	 * No. of projects owned by this customer
	 */
	private int noOfProjects;
}
