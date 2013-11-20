package com.jsp.MyYouTube;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.CreateDistributionRequest;
import com.amazonaws.services.cloudfront.model.CreateStreamingDistributionRequest;
import com.amazonaws.services.cloudfront.model.DistributionConfig;
import com.amazonaws.services.cloudfront.model.Origin;
import com.amazonaws.services.cloudfront.model.Origins;
import com.amazonaws.services.cloudfront.model.S3Origin;
import com.amazonaws.services.cloudfront.model.S3OriginConfig;
import com.amazonaws.services.cloudfront.model.StreamingDistributionConfig;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Servlet implementation class InitializeServlet
 */
public class InitializeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String BUCKET_NAME = "cloudcompute";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitializeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		AmazonCloudFrontClient cloudfront = new AmazonCloudFrontClient(credentialsProvider);
		
		System.out.println("initial the website");
		
		ServletContext application = this.getServletContext();
		//create s3
		AmazonS3Manager manager = new AmazonS3Manager();
		try {
			manager.createS3Bucket();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//combine distribution
		
//		S3Origin origin = new S3Origin()
//        .withDomainName(BUCKET_NAME+"s3.amazonaws.com");
//
//        S3Origins origins = new Origins().withItems(items).withItems(origin);
//
//        DistributionConfig streamingDistributionConfig1 = new DistributionConfig()
//        .withCallerReference("unique-id-for-idempotency")
//        .withComment("Streaming CloudFront distribution")
//        .withDefaultRootObject("index.html")
//        .withEnabled(true)
//        .withOrigins(origins); 
			
		StreamingDistributionConfig streamingDistributionConfig = new StreamingDistributionConfig()
		.withCallerReference("unique-id-for-idempotency")
        .withComment("Streaming CloudFront distribution")
        .withEnabled(true);
		//include the with parameters 
		CreateStreamingDistributionRequest streamingDistribution = new CreateStreamingDistributionRequest()
		        .withStreamingDistributionConfig(streamingDistributionConfig);          
		cloudfront.createStreamingDistribution(streamingDistribution);
		
		
		
		
		DistributionConfig downloadingDistributionConfig = new DistributionConfig();
		CreateDistributionRequest downloadingDistribution = new CreateDistributionRequest()
		        .withDistributionConfig(downloadingDistributionConfig); 
		cloudfront.createDistribution(downloadingDistribution);
		super.init();
	}

	

}
