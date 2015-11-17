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
 * Represents a comment on an Issue
 * 
 * @author Prateek Mathur
 */

package com.allissues.data;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Comment {
	@Id
	/**
	 * Unique ID of the comment
	 */
	private Long commentId;

	@Parent
	/**
	 * Key of the Issue under which comment is added
	 */
	private Key<Issue> issueKey;
	
	/**
	 * Title of the Comment
	 */
	private String title;
	
	/**
	 * Body of the Comment
	 */
	private Text body;
	
	/**
	 * Email of the User who added the Comment
	 */
	private String useremail;
	
	/**
	 * Date on which comment is added
	 */
	private Date date;
	
	/**
	 * Public Constructor for Comment
	 */
	public Comment(String title, String body, String useremail)	{
		this.title = title;
		this.body = new Text(body);
		this.useremail = useremail;
		this.date = new Date();
	}
	
	/**
	 * Getter for Title
	 */
	public String getTitle()	{
		return this.title;
	}
	
	/**
	 * Getter for Body
	 */
	public String getBody()	{
		return this.body.getValue();
	}
	
	/**
	 * Getter for useremail
	 */
	public String getUserEmail()	{
		return this.useremail;
	}
	
	/**
	 * Getter for Date
	 */
	public String getDate()	{
		return this.date.toLocaleString();
	}
}
