package com.aws.awss3java.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

  @Value("${aws.region}")
  private String awsRegion;

  @Value("${aws.profile}")
  private String awsProfile;

  @Bean
  public AmazonS3 s3LocalClient() {

    return AmazonS3ClientBuilder.standard()
        .withRegion(awsRegion)
        .withCredentials(new ProfileCredentialsProvider(awsProfile))
        .build();
  }
}
