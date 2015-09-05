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
 * Represents an issue filed in the system, and contains fields to identify the same.
 * 
 * @author Prateek Mathur
 *
 */

package com.allissues.data;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Issue {
	/**
	 * Public constructor for Issue
	 */
	public Issue(Long issueId, String title, String description, int priority, String createdBy, String assignedTo, String deadline, boolean developerIssue)	{
		this.issueId = issueId;
		this.title = title;
		this.description = new Text(description);
		this.priority = priority;
		this.createdBy = createdBy;
		this.assignedTo = assignedTo;
		this.estimatedResolutionDate = deadline;
		this.developerIssue = developerIssue;
		
		this.status = "OPEN";
		this.actualResolutionDate = "";
	}
	
	@Id
	/**
	 * An ID that can uniquely identify the issue in the system
	 */
	private Long issueId;
	
	/**
	 * Title of the issue
	 */
	private String title;
	
	/**
	 * Details of the issue
	 */
	private Text description;
	
	/**
	 * Priority Level of the Issue
	 */
	private int priority;
	
	/**
	 * Email of the customer/developer who created the issue
	 */
	private String createdBy;
	
	/**
	 * Email of the developer to whom the project is assigned
	 */
	private String assignedTo;
	
	/**
	 * Status of the issue (Open Or Closed)
	 */
	private String status; 
	
	/**
	 * Estimated resolution date, set either by the customer or the developer
	 */
	private String estimatedResolutionDate;
	
	/**
	 * Actual resolution date, on which the issue was resolved
	 */
	private String actualResolutionDate;
	
	/**
	 *  Represents if this issue was created by a developer. Can be used to exclude the issue from a customer's search,
	 *  or hide it on the customer home page.
	 */
	private boolean developerIssue;	
}
