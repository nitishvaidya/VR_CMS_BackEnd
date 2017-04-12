package com.vr.cms.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DBManager {

	private static Connection con;

	private static final DBManager dbManager = new DBManager();

	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vr_master_server?autoReconnect=true", "root",
					"Stream_Phony0");
			System.out.println("**************DB Connection Succesfull***************" + con);
			updateAllMapsFromDB();
		} catch (Exception e) {

			System.out.println("DB connection failed");
		}
	}

	private void updateAllMapsFromDB() {
		loadServerIds();
		loadVideoList();
	}

	private void loadVideoList() {

	}

	private void loadServerIds() {
		try {
			GenerateLocalServerId.setUuids(new HashSet<String>(getServerIds()));
			System.out.println("loadServerIds : " + GenerateLocalServerId.getUuids());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DBManager getDBManager() {
		if (dbManager == null) {
			System.out.println("Creating new instance of DB Manager           ");
			return new DBManager();
		}
		System.out.println("returning old instance of DB manager" + dbManager);
		return dbManager;
	}

	public void saveServerId(String serverId, School school) throws SQLException {
		System.out.println(con);
		Statement stmt = con.createStatement();
		System.out.println("insert into server_ids(server_id,school_name,address,city,website,phone) values(" + "\""
				+ serverId + "\"" + ",\"" + school.getName() + "\"" + ",\"" + school.getAddress() + "\"" + ",\""
				+ school.getCity() + "\"" + ",\"" + school.getWebsite() + "\"" + ",\"" + school.getPhone() + "\""
				+ ")");
		int res = stmt.executeUpdate("insert into server_ids(server_id,school_name,address,city,website,phone) values("
				+ "\"" + serverId + "\"" + ",\"" + school.getName() + "\"" + ",\"" + school.getAddress() + "\"" + ",\""
				+ school.getCity() + "\"" + ",\"" + school.getWebsite() + "\"" + ",\"" + school.getPhone() + "\""
				+ ")");

	}

	public List<String> getServerIds() throws SQLException {
		System.out.println(con);
		Statement stmt = con.createStatement();
		List<String> serverIds = new ArrayList<String>();
		ResultSet res = stmt.executeQuery("select server_id from server_ids");
		while (res.next()) {
			serverIds.add(res.getString("server_id"));
		}
		return serverIds;

	}

	public List<SchoolServer> getAllSchools() throws SQLException {
		System.out.println(con);
		Statement stmt = con.createStatement();
		List<SchoolServer> serverIds = new ArrayList<SchoolServer>();
		ResultSet res = stmt.executeQuery("select server_id,school_name from server_ids");
		while (res.next()) {
			SchoolServer schoolServer = new SchoolServer(res.getString("server_id"), res.getString("school_name"));
			serverIds.add(schoolServer);
		}

		System.out.println(serverIds);

		return serverIds;

	}

	public int addVideo(VideoMetadata video, List<String> serverIds) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.println(
				"insert into videos (name,type,category,imageName,imageDir,videoName,videoDir,description,timestamp) values("
						+ "\'" + video.getName() + "\'" + "," + "\'" + video.getType() + "\'" + "," + "\'"
						+ video.getCategory() + "\'" + "," + "\'" + video.getImageName() + "\'" + "," + "\'"
						+ video.getImageDir() + "\'" + "," + "\'" + video.getVideoName() + "\'" + "," + "\'"
						+ video.getVideoDir() + "\'" + "," + "\'" + video.getDescription() + "\'" + ","
						+ video.getTimestamp() + ")");
		int res = stmt.executeUpdate(
				"insert into videos (name,type,category,imageName,imageDir,videoName,videoDir,description,timestamp) values("
						+ "\'" + video.getName() + "\'" + "," + "\'" + video.getType() + "\'" + "," + "\'"
						+ video.getCategory() + "\'" + "," + "\'" + video.getImageName() + "\'" + "," + "\'"
						+ video.getImageDir() + "\'" + "," + "\'" + video.getVideoName() + "\'" + "," + "\'"
						+ video.getVideoDir() + "\'" + "," + "\'" + video.getDescription() + "\'" + ","
						+ video.getTimestamp() + ")",
				Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
		int autoIncKeyFromApi = -1;
		if (rs.next()) {
			autoIncKeyFromApi = rs.getInt(1);
		}
		System.out.println(res);
		if (serverIds != null && !serverIds.isEmpty()) {
			for (String serverId : serverIds) {
				System.out.println(
						"insert into servers_to_videos(server_id,video_id) values((select id from server_ids where server_id = "
								+ "\'" + serverId + "\'" + ")," + autoIncKeyFromApi + ")");
				stmt.addBatch(
						"insert into servers_to_videos(server_id,video_id) values((select id from server_ids where server_id = "
								+ "\'" + serverId + "\'" + ")," + autoIncKeyFromApi + ")");
			}
		}

		stmt.executeBatch();

		return autoIncKeyFromApi;

	}

	public List<VideoMetadata> getAllNewVideos(String serverId, String timestamp) {
		List<VideoMetadata> listOfVideos = new ArrayList<>();

		ResultSet res;
		try {
			Statement stmt = con.createStatement();
			System.out.println(
					"select * from videos where id in (select video_id from servers_to_videos where server_id = (select id from server_ids where server_id = "
							+ "\'" + serverId + "\'" + ")) and timestamp > " + timestamp);
			res = stmt.executeQuery(
					"select * from videos where id in (select video_id from servers_to_videos where server_id = (select id from server_ids where server_id = "
							+ "\'" + serverId + "\'" + ")) and timestamp > " + timestamp);

			while (res.next()) {
				VideoMetadata videoData = new VideoMetadata();

				int id = res.getInt("id");
				String name = res.getString("name");
				String type = res.getString("type");
				String category = res.getString("category");
				String imageName = res.getString("imageName");
				String imageDir = res.getString("imageDir");
				String videoName = res.getString("videoName");
				String videoDir = res.getString("videoDir");
				String description = res.getString("description");
				Long timeStamp = res.getLong("timestamp");

				videoData.setId(id);
				videoData.setName(name);
				videoData.setType(type);
				videoData.setCategory(category);
				videoData.setImageName(imageName);
				videoData.setImageDir(imageDir);
				videoData.setVideoName(videoName);
				videoData.setVideoDir(videoDir);
				videoData.setDescription(description);
				videoData.setTimestamp(timeStamp);

				System.out.println(videoData);
				listOfVideos.add(videoData);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfVideos;
	}

	public void saveSchool(School school) {
		// TODO Auto-generated method stub

	}

}
