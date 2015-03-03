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
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int priority = Integer.parseInt(request.getParameter("priority"));
		
		try {
			Date deadline = new SimpleDateFormat("yyyy/mm/dd").parse(request.getParameter("deadline"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
