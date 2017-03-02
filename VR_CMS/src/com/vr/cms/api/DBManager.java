package com.vr.cms.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

	private static Connection con;

	private static final DBManager dbManager = new DBManager();

	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/digitalwall", "root", "digit@lWa11");
			System.out.println("**************DB Connection Succesfull***************" + con);
		} catch (Exception e) {

			System.out.println("DB connection failed");

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

	public void saveSchoolCode(String schoolCode) throws SQLException {
		System.out.println(con);
		Statement stmt = con.createStatement();
		int res = stmt.executeUpdate("insert into schoolCodes(schoolCode) values(" + schoolCode + ")");

	}

	public List<String> getSchoolCodes() throws SQLException {
		System.out.println(con);
		Statement stmt = con.createStatement();
		List<String> schoolCodesList = new ArrayList<String>();
		ResultSet res = stmt.executeQuery("select schoolCode from schoolCodes");
		while (res.next()) {
			schoolCodesList.add(res.getString("schoolCode"));
		}
		return schoolCodesList;

	}

	public void saveUserId(String user, String schoolCode) throws SQLException {
		Statement stmt = con.createStatement();
		int res = stmt.executeUpdate(
				"insert into users(schoolCodeId,userId) values((select id from schoolCodes where schoolCode ="
						+ schoolCode + "),'" + user + "')");
	}

	public void updateToken(String user, String token) throws SQLException {
		Statement stmt = con.createStatement();
		int res = stmt.executeUpdate("update users set token = '" + token + "' where userId = '" + user + "'");
	}

}
