//package com.jsp.MyYouTube;
//
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.Enumeration;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//@SuppressWarnings("serial")
//public class ServletUpload extends HttpServlet {
//
//	private final String LOCAL_PATH = "/Users/LarryCane/Code/Java/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/MyYouTube/temp";
//	private final String SERVER_PATH = "/home/bitnami/stack/apache-tomcat/webapps/MyYouTube/temp/";
//
//	/**
//	 * The doPost method of the servlet. <br>
//	 *
//	 * This method is called when a form has its tag value method equals to post.
//	 * 
//	 * @param request the request send by the client to the server
//	 * @param response the response send by the server to the client
//	 * @throws ServletException if an error occurred
//	 * @throws IOException if an error occurred
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		System.out.println(new File(".").getCanonicalPath());
//		RDSManager rdsManager = null;
//		try {
//			rdsManager = new RDSManager();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		boolean isMulti = ServletFileUpload.isMultipartContent(request);
//		AmazonS3Manager s3Manager = new AmazonS3Manager();
//		System.out.println("Reaching here#1");
//		if (isMulti) {
//			ServletFileUpload upload = new ServletFileUpload();
//			System.out.println("Reaching here#2");
//			try {
//				FileItemIterator iter = upload.getItemIterator(request);
//				while (iter.hasNext()) {
//					FileItemStream item = iter.next();
//					InputStream inputStream = item.openStream();
//					if (item.isFormField()) {
//						
//					} else {
//						System.out.println("Reaching here#3");
//						String fileName = item.getName();
//						System.out.println("The file name is " + fileName);
//						System.out.println("Filename length : " + fileName.length());
//						if (fileName != null && fileName.length() > 0) {
//							//read stream of file uploaded
//							byte b[] = new byte[1024];
//							String filePath = LOCAL_PATH + fileName;
//							File file = new File(filePath);
//							DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
//							System.out.println("Reaching here#4");
//							int read = 0;
//							while ((read = inputStream.read(b)) != -1) {
//								dataOutputStream.write(b, 0, read);
//							}
//							dataOutputStream.close();
//							inputStream.close();
//							//store as a temporary file
//							System.out.println("Reaching here#5");
//							//upload the file to s3
//							String temp[] = new String[2];
//							temp = fileName.split("\\.");
//							s3Manager.createS3Bucket();
//							s3Manager.uploadObject(filePath, temp[0]);
//							// Update database
//							rdsManager.insertRecord(temp[0]);
//							System.out.println("The rating is " + rdsManager.readObjectRating(temp[0]));
//							// Delete temp file
//							file.delete();
//						}
//					}
//				}
//			} catch (Exception e) {
//			}
//		}
//		
//		
//		response.sendRedirect("/MyYouTube/MainPage.jsp");
//	}
//
//	
//
//}
