package com.jsp.MyYouTube;

import java.sql.Timestamp;
import java.util.*;

public class VideoInfo {
	public int ID;
	public String name;
	public String uploadTime;
	public double avgRate;
	public int rateTimes;
	public String url;
	public VideoInfo(int iD, String name, double avgRate, String url) {
		super();
		ID = iD;
		this.name = name;
		this.avgRate = avgRate;
		this.url = url;
	}
	public VideoInfo(String name, String uploadTime, String url) {
		super();
		this.name = name;
		this.uploadTime = uploadTime;
		this.url = url;
	}
	public VideoInfo(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}
	public VideoInfo(int iD, String name, String uploadTime, double avgRate,
			int rateTimes, String url) {
		super();
		ID = iD;
		this.name = name;
		this.uploadTime = uploadTime;
		this.avgRate = avgRate;
		this.rateTimes = rateTimes;
		this.url = url;
	}
	
}

