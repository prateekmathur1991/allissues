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
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GlobalServlet
 */
public class GlobalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(GlobalServlet.class.getName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		logger.info("Request URI::" + requestUri);
		
		String url = requestUri.substring(requestUri.lastIndexOf("/") + 1);
		String sub_url = "";
		if (url.indexOf("/") != -1)	{
			sub_url = url.substring(0, url.lastIndexOf("/"));
			url = url.substring(url.lastIndexOf("/"));
		}
		
		logger.info("URL::" + url + " Sub URL::" + sub_url);
		
		if ("issue".equalsIgnoreCase(url))	{
			request.getRequestDispatcher("/viewissue.jsp?issueid=").forward(request, response);
		} else if ("profile".equalsIgnoreCase(url))	{
			request.getRequestDispatcher("/viewprofile.jsp?profileid=").forward(request, response);
		}
	}

}
