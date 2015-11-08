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

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Issue {
	@Id
	/**
	 * An ID that can uniquely identify the issue in the system
	 */
	private Long issueId;
	
	/**
	 * Key of the project under which the issue is created
	 */
	@Parent
	private Key<Project> projectKey;
	
	/**
	 * Title of the issue
	 */
	@Index
	private String title;
	
	/**
	 * Details of the issue
	 */
	private Text description;
	
	/**
	 * Priority Level of the Issue
	 */
	@Index
	private int priority;
	
	/**
	 * Email of the customer/developer who created the issue
	 */
	@Index
	private String createdBy;
	
	/**
	 * Email of the developer to whom the project is assigned
	 */
	@Index
	private String assignedTo;
	
	/**
	 * Status of the issue (Open Or Closed)
	 */
	@Index
	private String status; 
	
	/**
	 * Estimated resolution date, set either by the customer or the developer
	 */
	@Index
	private Date estimatedResolutionDate;
	
	/**
	 * Actual resolution date, on which the issue was resolved
	 */
	@Index
	private Date actualResolutionDate;
	
	/**
	 *  Represents if this issue was created by a developer. Can be used to exclude the issue from a customer's search,
	 *  or hide it on the customer home page.
	 */
	@Index
	private boolean developerIssue;
	
	/**
	 * Public constructor for Issue
	 */
	public Issue(Key<Project> projectKey, String title, String description, int priority, String createdBy, String assignedTo, String deadline, boolean developerIssue)	{
		this.projectKey = projectKey;
		this.title = title;
		this.description = new Text(description);
		this.priority = priority;
		this.createdBy = createdBy;
		this.assignedTo = assignedTo;
		this.estimatedResolutionDate = new Date(deadline);
		this.developerIssue = developerIssue;
		
		this.status = "OPEN";
		this.actualResolutionDate = null;
	}
	
	/**
	 * Getter for Title
	 */
	public String getTitle()	{
		return title;
	}
	
	/**
	 * Getter for Description
	 */
	public String getDescription()	{
		return description.getValue();
	}
	
	/**
	 * Getter for Priority
	 */
	public int getPriority()	{
		return priority;
	}
	
	/**
	 * Getter for createdBy
	 */
	public String getCreatedBy()	{
		return createdBy;
	}
	
	/**
	 * Getter for assignedTo
	 */
	public String getAssignedTo()	{
		return assignedTo;
	}
	
	/**
	 * Getter for status
	 */
	public String getStatus()	{
		return status;
	}
	
	/**
	 * Getter for estimatedResolutionDate
	 */
	public String getEstimatedResolutionDate()	{
		return estimatedResolutionDate.toGMTString();
	}
	
	/**
	 * Getter for actualResolutionDate
	 */
	public String getActualResolutionDate()	{
		return actualResolutionDate.toGMTString();
	}
	
	/**
	 * Getter for weatherDeveloperIssue
	 */
	public boolean isDeveloperIssue()	{
		return developerIssue;
	}
	
	/**
	 * Getter for project key
	 */
	public Key<Project> getProjectKey()	{
		return this.projectKey;
	}
	
	// Making the default constructor private
	private Issue()	{}
}