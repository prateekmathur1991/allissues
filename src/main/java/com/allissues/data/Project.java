
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

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Project {
	@Id
	/**
	 *  An ID that can uniquely identify the project in the system
	 */
	private String projectId;
	
	/**
	 * Name of the project
	 */
	private String name;
	
	/**
	 * Reference to the customer object who owns the project
	 */
	private Customer owner;
	
	/**
	 * No. of bugs filed for this project
	 */
	private int noOfBugs;
}
