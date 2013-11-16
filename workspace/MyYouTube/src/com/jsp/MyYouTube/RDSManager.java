package com.jsp.MyYouTube;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.mysql.jdbc.Statement;

public class RDSManager {
	private String jdbcUrl;
	private String dbName = "MyYouTube"; 
	private String userName = "MyYouTube"; 
	private String password = "polymyyoutube"; 
	private String hostname = "assignment2.cbg6bqxtgzic.us-east-1.rds.amazonaws.com";
	private String port = "3306";
	//private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	//private ResultSet resultSet = null;
	private Connection connection = null;
	
	public RDSManager() throws SQLException, ClassNotFoundException, InterruptedException {

		init();
	}

	private void init() throws SQLException, ClassNotFoundException, InterruptedException {
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		new AmazonRDSClient(credentialsProvider);
		//make connection to RDS
		jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(jdbcUrl);
		System.out.println("Connect to RDS successfully");
	}
	
	public void dropTable() throws SQLException {
		String dropTableQuery = "DROP TABLE VideoRating";
		preparedStatement = connection.prepareStatement(dropTableQuery);
		preparedStatement.execute();
	}
	
//	public void createTable() throws SQLException {
//		String createTableQuery = "CREATE TABLE VideoRating (ID VideoName TINYTEXT, AvgRating DOUBLE(2,1), RatingTimes INT(10))";
//		
//		preparedStatement = connection.prepareStatement(createTableQuery);
//		preparedStatement.execute();
//		
//	}
	
	public double readObjectRating(int ID) throws InterruptedException, SQLException {
		double rating = 0;
		String selectQuery = "SELECT * FROM VideoRating WHERE ID = ?";
		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, ID);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			rating = resultSet.getDouble("avgRate");
		}
		return rating;
			
	}

	public List<VideoInfo> getVideoList() throws SQLException {
		List videoList = new ArrayList<VideoInfo>();
		String getVideoListQuery = "SELECT * FROM VideoRating";
		preparedStatement = connection.prepareStatement(getVideoListQuery);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int ID = resultSet.getInt("ID");
			String name = resultSet.getString("name");
			Double avgRate = resultSet.getDouble("avgRate");
			String url = resultSet.getString("url");
			int rateTimes = resultSet.getInt("rateTimes");
			String upload = resultSet.getString("uploadTime");
			System.out.println("The video name is " + name + ", the rating is " + avgRate);
			//videoList.put(videoName, avgRating);
			videoList.add(new VideoInfo(ID,name,upload,avgRate,rateTimes,url));
		}
		return videoList;
	}
	
	public void updateRating(int ID, int currentRate) throws SQLException {
		String selectQuery = "SELECT * FROM VideoRating WHERE ID = ?";
		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setInt(1, ID);
		ResultSet resultSet = preparedStatement.executeQuery();
		double avgRating = 0;
		int ratingTimes = 0;
		while(resultSet.next()){
			avgRating = resultSet.getDouble("avgRate");
			ratingTimes = resultSet.getInt("rateTimes");
			double sum = avgRating*ratingTimes + currentRate;
			ratingTimes++;
			double rating = sum/ratingTimes;
			System.out.println("Sum: " + sum + "; Former avgRating: " + avgRating + "; rating: "  + rating);
			String updateQuery = "UPDATE VideoRating SET AvgRating = ?, RatingTimes = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setDouble(1, rating);
			preparedStatement.setInt(2, ratingTimes);
			preparedStatement.setInt(3, ID);
			preparedStatement.executeUpdate();
		}
		
	}
	
	public void insertRecord(VideoInfo video){
		try{
		System.out.println("Start insert");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String insertQuery = "INSERT INTO VideoRating(name,uploadTime,url) "
				+ "VALUES ('"+video.name+"', '"+dateFormat.format(date)+"', '"+video.url+"');";
		System.out.println(insertQuery);
		Statement st = (Statement) connection.createStatement();
		st.execute(insertQuery);
//		preparedStatement = connection.prepareStatement(insertQuery);
//		preparedStatement.setString(1, video.name);
//		preparedStatement.setTimestamp(2, timestamp);
//		preparedStatement.setString(3, video.url);
//		preparedStatement.execute();
		System.out.println("End insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void deleteRecord(int ID) throws SQLException {
		String deleteQuery = "DELETE FROM VideoRating WHERE ID = ?";
		preparedStatement = connection.prepareStatement(deleteQuery);
		preparedStatement.setInt(1, ID);
		boolean result = preparedStatement.execute();
		if (result) {
			System.out.println("The record has been deleted");
		}
	}
}
