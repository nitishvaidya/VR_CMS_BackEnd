package com.vr.cms.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetAllNewVideos
 */
@WebServlet(description = "GetAllNewVideos", urlPatterns = { "/getAllNewVideos" })
public class GetAllNewVideos extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8220052809035823440L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllNewVideos() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Gson json = new Gson();
		response.setHeader("Access-Control-Allow-Origin", "*");
		String serverId = request.getParameter("serverId");
		String lastUpdatedTime = request.getParameter("lastUpdatedTime");
		if (!Utility.isStringEmpty(serverId) && !Utility.isStringEmpty(lastUpdatedTime)) {
			if (isValidServerId(serverId)) {
				Map<Long, VideoMetadata> videoList = Videos.getListOfVideos(serverId);
				if (Utility.isNotNullOrEmpty(videoList)) {
					List<VideoMetadata> list = new ArrayList<>();
					for (Map.Entry<Long, VideoMetadata> entry : videoList.entrySet()) {
						if (entry.getKey() > Long.parseLong(lastUpdatedTime)) {
							list.add(entry.getValue());
						}
					}
					System.out.println("GetAllVideos : json.toJson(list)  " + json.toJson(list));
					response.getWriter().append(json.toJson(list));
				}
			}
		} else {
			response.getWriter().append("Served at: ").append(request.getContextPath())
					.append("  Error: Bad Request : One or more parameter is empty");
		}

	}

	private boolean isValidServerId(String serverId) {
		System.out.println(GenerateLocalServerId.getUuids());
		return GenerateLocalServerId.getUuids().contains(serverId);
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
