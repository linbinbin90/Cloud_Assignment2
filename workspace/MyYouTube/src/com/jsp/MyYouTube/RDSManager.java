package com.jsp.MyYouTube;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.rds.AmazonRDSClient;

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
		System.out.println("Reaching here#1");
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Reaching here#2");
		connection = DriverManager.getConnection(jdbcUrl);
		System.out.println("Reaching here#3");
	}
	
	public void dropTable() throws SQLException {
		String dropTableQuery = "DROP TABLE VideoRating";
		preparedStatement = connection.prepareStatement(dropTableQuery);
		preparedStatement.execute();
		System.out.println("Reaching here#6");
	}
	
	public void createTable() throws SQLException {
		String createTableQuery = "CREATE TABLE VideoRating (VideoName TINYTEXT, AvgRating DOUBLE(2,1), RatingTimes INT(10))";
		
		preparedStatement = connection.prepareStatement(createTableQuery);
		preparedStatement.execute();
		System.out.println("Reaching here#5");
		
	}
	
	public double readObjectRating(String videoName) throws InterruptedException, SQLException {
		double rating = 0;
		String selectQuery = "SELECT * FROM VideoRating WHERE VideoName = ?";
		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setString(1, videoName);
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			rating = resultSet.getDouble("AvgRating");
		}
		return rating;
			
	}

	public Map<String, Double> getVideoList() throws SQLException {
		Map<String, Double> videoList = new HashMap<String, Double>();
		String getVideoListQuery = "SELECT * FROM VideoRating";
		preparedStatement = connection.prepareStatement(getVideoListQuery);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			String videoName = resultSet.getString("VideoName");
			Double avgRating = resultSet.getDouble("AvgRating");
			System.out.println("The video name is " + videoName + ", the rating is " + avgRating);
			videoList.put(videoName, avgRating);
		}
		return videoList;
	}
	
	public void updateRating(String videoName, int currentRate) throws SQLException {
		String selectQuery = "SELECT * FROM VideoRating WHERE VideoName = ?";
		preparedStatement = connection.prepareStatement(selectQuery);
		preparedStatement.setString(1, videoName);
		ResultSet resultSet = preparedStatement.executeQuery();
		double avgRating = 0;
		int ratingTimes = 0;
		while(resultSet.next()){
			avgRating = resultSet.getDouble("AvgRating");
			ratingTimes = resultSet.getInt("RatingTimes");
			double sum = avgRating*ratingTimes + currentRate;
			ratingTimes++;
			double rating = sum/ratingTimes;
			System.out.println("Sum: " + sum + "; Former avgRating: " + avgRating + "; rating: "  + rating);
			String updateQuery = "UPDATE VideoRating SET AvgRating = ?, RatingTimes = ? WHERE VideoName = ?";
			preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setDouble(1, rating);
			preparedStatement.setInt(2, ratingTimes);
			preparedStatement.setString(3, videoName);
			preparedStatement.executeUpdate();
		}
		
	}
	
	public void insertRecord(String videoName) throws SQLException {
		String insertQuery = "INSERT INTO VideoRating VALUES (?, 0, 0)";
		preparedStatement = connection.prepareStatement(insertQuery);
		preparedStatement.setString(1, videoName);
		preparedStatement.execute();
	}
	
	public void deleteRecord(String videoName) throws SQLException {
		String deleteQuery = "DELETE FROM VideoRating WHERE VideoName = ?";
		preparedStatement = connection.prepareStatement(deleteQuery);
		preparedStatement.setString(1, videoName);
		boolean result = preparedStatement.execute();
		if (result) {
			System.out.println("The record has been deleted");
		}
	}
}
