package com.vr.cms.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddOrUpdateSchool
 */
@WebServlet(description = "AddOrUpdateSchool", urlPatterns = { "/addOrUpdateSchool" })
public class AddOrUpdateSchool extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8976310478406661326L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrUpdateSchool() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");

		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String website = request.getParameter("website");
		String phone = request.getParameter("phone");

		System.out.println("name : " + name + ", address : " + address + ",city : " + city + ", website : " + website
				+ ", phone : " + phone);

		if (Utility.isStringEmpty(name) || Utility.isStringEmpty(address) || Utility.isStringEmpty(city)
				|| Utility.isStringEmpty(phone)) {
			response.getWriter().append("One or More Required input is Empty");
		} else {
			System.out.println("inside saving school");
			School school = addSchool(name, address, city, website, phone);
			System.out.println("AddOrUpdateSchool: School  " + school);
			String uuid = null;
			try {
				uuid = GenerateLocalServerId.generateUserIds();
				DBManager.getDBManager().saveServerId(uuid, school);
				response.getWriter().append("Generated UUID for school : " + school.getName() + ":	").append(uuid);
			} catch (SQLException e) {
				response.getWriter().append("Error Occured : Please try again ");
				e.printStackTrace();
			}

		}
	}

	private School addSchool(String name, String address, String city, String website, String phone) {
		School school = new School();
		school.setName(name);
		school.setAddress(address);
		school.setCity(city);
		school.setWebsite(website);
		school.setPhone(phone);
		return school;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
