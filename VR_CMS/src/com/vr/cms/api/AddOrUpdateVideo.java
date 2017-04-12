package com.vr.cms.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class AddOrUpdateVideo
 */
@WebServlet(description = "AddOrUpdateVideo", urlPatterns = { "/addOrUpdateVideo" })
@MultipartConfig
public class AddOrUpdateVideo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8976310478406661326L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddOrUpdateVideo() {
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
		String imageDir = "/usr/local/apache-tomcat-7.0.72/apache-tomcat-7.0.72/webapps/VR_CMS/images/";
		String videoName = request.getParameter("videoName");
		String videoDir = "/usr/local/apache-tomcat-7.0.72/apache-tomcat-7.0.72/webapps/VR_CMS/videos/";
		String description = request.getParameter("description");
		String listOfServers = request.getParameter("serverList");

		System.out.println("name : " + name + ", type : " + type + ",category : " + category + ", imageName : "
				+ imageName + ", imageDir : " + imageDir + ", videoName " + videoName + ", videoDir : " + videoDir
				+ ", description : " + description + ", serverList : " + listOfServers);

		Part imageFile = request.getPart("image");

		System.out.println(imageFile);

		Part videoFile = request.getPart("video");

		uploadFile(response, imageDir, imageFile, imageName);
		uploadFile(response, videoDir, videoFile, videoName);

		if (Utility.isStringEmpty(name) || Utility.isStringEmpty(type) || Utility.isStringEmpty(category)
				|| Utility.isStringEmpty(imageName) || Utility.isStringEmpty(imageDir)
				|| Utility.isStringEmpty(videoName) || Utility.isStringEmpty(videoDir)
				|| Utility.isStringEmpty(description)) {
			response.getWriter().append("One or More Required input is Empty");
		} else {
			System.out.println("inside saving video");
			List<String> serverList = null;
			System.out.println(listOfServers);
			if (!Utility.isStringEmpty(listOfServers)) {
				String[] servers = listOfServers.split(",");
				serverList = Arrays.asList(servers);
				System.out.println("Saving new video");
				VideoMetadata newVideo = addVideo(name, type, category, imageName, imageDir, videoName, videoDir,
						description, serverList);
				System.out.println("AddOrUpdateVideo: Videos  " + Videos.getUserIdToVideos());
				response.getWriter().append("Served at: ").append(request.getContextPath()).append(newVideo.toString());
			}
		}
	}

	private void uploadFile(HttpServletResponse response, String dir, Part file, String fileName) throws IOException {
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();
		System.out.println("Uploading File " + fileName);

		try {
			out = new FileOutputStream(new File(dir + File.separator + fileName));
			filecontent = file.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			writer.println("New file " + fileName + " created at " + dir);
			System.out.println("File{0}being uploaded to {1}" + new Object[] { fileName, dir });
		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());

			System.out.println("Problems during file upload. Error: {0}" + new Object[] { fne.getMessage() });
			fne.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("Part Header = {0}" + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
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
