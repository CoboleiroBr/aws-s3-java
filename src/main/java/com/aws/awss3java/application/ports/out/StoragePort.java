package com.aws.awss3java.application.ports.out;

import com.aws.awss3java.application.exception.ResourceException;
import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {

  void createBucket(String bucketName) throws ResourceException;

  void deleteBucket(String bucketName) throws ResourceException;

  void putObject(String bucketName, String objectFileName, String fileName, MultipartFile file)
      throws ResourceException;
}
