package com.jsp.MyYouTube;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.fileupload.FileItemIterator;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AmazonS3Manager {
	
	private AmazonS3Client s3Client;
	
	private final String BUCKET_NAME = "cloudcompute";
	
	public AmazonS3Manager() {

		init();
	}

	private void init() {
		
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		s3Client = new AmazonS3Client(credentialsProvider);
		
		
	}

	void createS3Bucket() throws InterruptedException {
		List<Bucket> buckets = s3Client.listBuckets();
		for(Bucket bucket : buckets){
			if (bucket.getName().equals(BUCKET_NAME)) {
				System.out.println("The bucket is exist");
				return;
			}
		}
		
		s3Client.createBucket(BUCKET_NAME);
		System.out.println("A new bucket has been created");
		
	}

	void uploadObject(String filePath,String filename) {
		File file = new File(filePath);
		String temp[] = new String[2];
		temp = filename.split("\\.");
		PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, temp[0], file);
		AccessControlList accessControlList = new AccessControlList();
		accessControlList.grantPermission(GroupGrantee.AllUsers,Permission.Read);
		putObjectRequest.withAccessControlList(accessControlList);
		s3Client.putObject(putObjectRequest);
		
	}

	List<String> readAllFilesFromS3() {
		List<String> fileList = new LinkedList<String>();
		
		return fileList;
	}

	public void deleteObject(String fileName) {
		s3Client.deleteObject(BUCKET_NAME, fileName);
	}
}
