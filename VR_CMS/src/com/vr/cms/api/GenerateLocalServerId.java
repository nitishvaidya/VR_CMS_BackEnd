package com.vr.cms.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenerateLocalServerId
 */
@WebServlet(description = "GenerateLocalServerId", urlPatterns = { "/generateLocalServerId" })
public class GenerateLocalServerId extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6230092824272353844L;

	private static Set<String> uuids = new HashSet<>();

	public static Set<String> getUuids() {
		return uuids;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateLocalServerId() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");

		String token = request.getParameter("token");
		if (!Utility.isStringEmpty(token) && validate(token)) {
			String serverId = null;
			serverId = generateUserIds();
			response.getWriter().append(serverId);
			System.out.println("GenerateUserId:Server Id  " + serverId);
			System.out.println("Total servers : " + uuids);
		}
	}

	private boolean validate(String token) {
		return token.equals("xxxx");
	}

	private String generateUserIds() {
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		if (!uuids.contains(userId)) {
			uuids.add(userId);
		} else {
			generateUserIds();
		}
		return userId;
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
