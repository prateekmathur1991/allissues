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

package com.allissues.logic;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateIssue
 */
public class CreateIssue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title;
		String description;
		int priority;
		Date deadline;
		
		try {
			title = request.getParameter("title");
			description = request.getParameter("description");
			priority = Integer.parseInt(request.getParameter("priority"));
			deadline = new SimpleDateFormat("yyyy/mm/dd").parse(request.getParameter("deadline"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}

}
