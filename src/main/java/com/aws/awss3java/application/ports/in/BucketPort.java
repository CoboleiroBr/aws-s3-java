package com.aws.awss3java.application.ports.in;

import com.aws.awss3java.application.exception.ResourceException;

public interface BucketPort {

  void create(String bucketName) throws ResourceException;

  void delete(String bucketName) throws ResourceException;
}
