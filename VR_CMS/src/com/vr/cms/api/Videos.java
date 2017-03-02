package com.vr.cms.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Videos {

	private static Map<String, Map<Long, VideoMetadata>> userIdToVideos = new ConcurrentHashMap<String, Map<Long, VideoMetadata>>();

	public static Map<String, Map<Long, VideoMetadata>> getUserIdToVideos() {
		return userIdToVideos;
	}

	public static Map<Long, VideoMetadata> getListOfVideos(String serverId) {
		return userIdToVideos.get(serverId);
	}

}
