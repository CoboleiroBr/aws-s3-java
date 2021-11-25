package com.aws.awss3java.adapter.out;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;
import com.aws.awss3java.application.exception.BucketException;
import com.aws.awss3java.application.exception.ResourceException;
import com.aws.awss3java.application.ports.out.StoragePort;
import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmazonS3Adapter implements StoragePort {

  private final AmazonS3 s3Client;

  @Override
  public void createBucket(String bucketName) throws ResourceException {

    try {
      if (!s3Client.doesBucketExistV2(bucketName)) {
        // Because the CreateBucketRequest object doesn't specify a region, the
        // bucket is created in the region specified in the client.
        s3Client.createBucket(new CreateBucketRequest(bucketName));

        // Verify that the bucket was created by retrieving it and checking its location.
        String bucketLocation = s3Client.getBucketLocation(
            new GetBucketLocationRequest(bucketName));
        log.info("Bucket created at: {}", bucketLocation);
      }
      else {
        log.info("Bucket already existent!");
        throw new ResourceException("Bucket already existent!");
      }
    }
    catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it and returned an error response.
      log.error("Amazon ServiceException: " + e.getErrorMessage());
      throw new BucketException(e.getErrorMessage());
    }
    catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      log.error("SDK Client Exception: " + e.getMessage());
      throw new BucketException(e.getMessage());
    }
  }

  @Override
  public void deleteBucket(String bucketName) throws ResourceException {

    try {
      if (!s3Client.doesBucketExistV2(bucketName)) {
        log.info("Bucket not found!");
        throw new ResourceException("Bucket not found!");
      }

      ObjectListing objectListing = s3Client.listObjects(bucketName);
      while (true) {
        for (S3ObjectSummary s3ObjectSummary : objectListing.getObjectSummaries()) {
          s3Client.deleteObject(bucketName, s3ObjectSummary.getKey());
        }

        // If the bucket contains many objects, the listObjects() call might not return all of the
        // objects in the first listing. Check to see whether the listing was truncated. If so,
        // retrieve the next page of objects and delete them.
        if (objectListing.isTruncated()) {
          objectListing = s3Client.listNextBatchOfObjects(objectListing);
        }
        else {
          break;
        }
      }

      // Delete all object versions (required for versioned buckets).
      VersionListing versionList = s3Client.listVersions(
          new ListVersionsRequest().withBucketName(bucketName));
      while (true) {
        for (S3VersionSummary vs : versionList.getVersionSummaries()) {
          s3Client.deleteVersion(bucketName, vs.getKey(), vs.getVersionId());
        }

        if (versionList.isTruncated()) {
          versionList = s3Client.listNextBatchOfVersions(versionList);
        }
        else {
          break;
        }
      }

      // After all objects and object versions are deleted, delete the bucket.
      s3Client.deleteBucket(bucketName);
    }
    catch (AmazonServiceException e) {
      log.error("Amazon ServiceException: " + e.getErrorMessage());
      throw new BucketException(e.getErrorMessage());
    }
    catch (SdkClientException e) {
      log.error("SDK Client Exception: " + e.getMessage());
      throw new BucketException(e.getMessage());
    }
  }

  @Override
  public void putObject(String bucketName, String fileObjectKeyName, String fileName,
      MultipartFile file) throws ResourceException {

    try {
      // Upload a file as a new object with ContentType and title specified.
      File outputFile = new File(fileName);

      try (BufferedOutputStream stream = new BufferedOutputStream(
          new FileOutputStream(outputFile))) {
        stream.write(file.getBytes());
      }
      catch (IOException e) {
        log.error(e.getMessage());
      }

      PutObjectRequest request = new PutObjectRequest(bucketName, fileObjectKeyName, outputFile);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentType("image/jpeg");
      metadata.addUserMetadata("title", fileObjectKeyName);
      request.setMetadata(metadata);
      s3Client.putObject(request);
    }
    catch (AmazonServiceException e) {
      log.error("Amazon ServiceException: " + e.getErrorMessage());
      throw new BucketException(e.getErrorMessage());
    }
    catch (SdkClientException e) {
      log.error("SDK Client Exception: " + e.getMessage());
      throw new BucketException(e.getMessage());
    }
  }
}
