package com.vr.cms.api;

import java.io.Serializable;
import java.util.List;

public class VideoMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986653860670573155L;

	private Integer id;

	private String name;

	private String type;

	private String category;

	private String imageName;

	private String imageDir;

	private String videoName;

	private String videoDir;

	private String description;

	private Long timestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDir() {
		return videoDir;
	}

	public void setVideoDir(String videoDir) {
		this.videoDir = videoDir;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "VideoMetadata [id=" + id + ", name=" + name + ", type=" + type + ", category=" + category
				+ ", imageName=" + imageName + ", imageDir=" + imageDir + ", videoName=" + videoName + ", videoDir="
				+ videoDir + ", description=" + description + ", timestamp=" + timestamp + "]";
	}

}
