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
	 * Constructor for the Developer class, used to create a developer entity and save it in the datastore.
	 */
	public Developer(String email, String name,  String password)	{
		this.email = email;
		this.name = name;
		this.password = password;

		noOfBugsAssigned = 0;
		noOfBugsFixed = 0;
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
	
	// Making the default constructor private
	private Developer() {}
	
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
}
