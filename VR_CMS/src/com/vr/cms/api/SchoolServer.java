package com.vr.cms.api;

public class SchoolServer {

	private String serverId;
	private String schoolName;

	public SchoolServer(String serverId, String schoolName) {
		super();
		this.serverId = serverId;
		this.schoolName = schoolName;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	@Override
	public String toString() {
		return "SchoolServer [serverId=" + serverId + ", schoolName=" + schoolName + "]";
	}

}
