package com.vr.cms.api;

import java.util.Map;

public class Utility {

	private static int id = -1;

	public static boolean isStringEmpty(String value) {
		if (value == null || value.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static int getNextId() {
		return ++id;
	}

	public static boolean isNotNullOrEmpty(Map<Long, VideoMetadata> videoList) {
		return videoList != null && videoList.size() != 0;
	}

}
