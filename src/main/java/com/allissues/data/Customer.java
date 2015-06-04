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
 * Represents a Customer entity
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
	 * No. of projects owned by this customer
	 */
	private int noOfProjects;

	/**
	 * Constructor for the Customer class
	 */
	public Customer(String email, String name, String password)	{
		this.email = email;
		this.name = name;
		this.password = password;

		noOfBugsCreated = 0;
		noOfProjects = 0;
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
     * Getter for noOfProjects
     *
     * @return
     */
    public int getNoOfProjects() {
        return noOfProjects;
    }
}
