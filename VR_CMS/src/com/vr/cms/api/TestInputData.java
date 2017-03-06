package com.vr.cms.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddOrUpdateVideo
 */
@WebServlet(description = "TestInputData", urlPatterns = { "/testInputData" })
public class TestInputData extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8976310478406661326L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestInputData() {
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
		String type = request.getParameter("type");
		String category = request.getParameter("category");
		String imageName = request.getParameter("imageName");
		String imageDir = request.getParameter("imageDir");
		String videoName = request.getParameter("videoName");
		String videoDir = request.getParameter("videoDir");
		String description = request.getParameter("description");
		String listOfServers = request.getParameter("serverList");
		System.out.println("Data received "+name+":"+type+":"+category);
//		if (Utility.isStringEmpty(name) || Utility.isStringEmpty(type) || Utility.isStringEmpty(category)
//				|| Utility.isStringEmpty(imageName) || Utility.isStringEmpty(imageDir)
//				|| Utility.isStringEmpty(videoName) || Utility.isStringEmpty(videoDir)
//				|| Utility.isStringEmpty(description)) {
//			response.getWriter().append("One or More Required input is Empty");
//		} else {
//			System.out.println("inside saving video");
//			List<String> serverList = null;
//			System.out.println(listOfServers);
//			if (!Utility.isStringEmpty(listOfServers)) {
//				String[] servers = listOfServers.split(",");
//				serverList = Arrays.asList(servers);
//				System.out.println("Saving new video");
//				VideoMetadata newVideo = addVideo(name, type, category, imageName, imageDir, videoName, videoDir,
//						description, serverList);
//				System.out.println("AddOrUpdateVideo: Videos  " + Videos.getUserIdToVideos());
//				response.getWriter().append("Served at: ").append(request.getContextPath()).append(newVideo.toString());
//			}
//		}
	}

	private VideoMetadata addVideo(String name, String type, String category, String imageName, String imageDir,
			String videoName, String videoDir, String description, List<String> serverList) {
		VideoMetadata newVideo = createVideo(name, type, category, imageName, imageDir, videoName, videoDir,
				description);
		System.out.println(newVideo);
		int id = -1;
		try {
			id = DBManager.getDBManager().addVideo(newVideo, serverList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newVideo.setId(id);
		updateMaps(serverList, newVideo);
		System.out.println("going back");
		return newVideo;
	}

	private void updateMaps(List<String> serverList, VideoMetadata newVideo) {
		for (String server : serverList) {
			Map<Long, VideoMetadata> videos = Videos.getListOfVideos(server);
			if (Utility.isNotNullOrEmpty(videos)) {
				videos.put(newVideo.getTimestamp(), newVideo);
			} else {
				videos = new ConcurrentSkipListMap<>();
				videos.put(newVideo.getTimestamp(), newVideo);
				Videos.getUserIdToVideos().put(server, videos);
			}
		}

	}

	private VideoMetadata createVideo(String name, String type, String category, String imageName, String imageDir,
			String videoName, String videoDir, String description) {
		// TODO Auto-generated method stub
		VideoMetadata newVideo = new VideoMetadata();
		newVideo.setName(name);
		newVideo.setType(type);
		newVideo.setCategory(category);
		newVideo.setImageName(imageName);
		newVideo.setImageDir(imageDir);
		newVideo.setVideoName(videoName);
		newVideo.setVideoDir(videoDir);
		newVideo.setDescription(description);
		newVideo.setTimestamp(System.currentTimeMillis());
		return newVideo;
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
