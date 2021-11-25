package com.aws.awss3java.application.usecase;

import com.aws.awss3java.application.exception.ResourceException;
import com.aws.awss3java.application.ports.in.BucketPort;
import com.aws.awss3java.application.ports.out.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BucketUseCase implements BucketPort {

  private final StoragePort storagePort;

  @Override
  public void create(String bucketName) throws ResourceException {

    storagePort.createBucket(bucketName);
  }

  @Override
  public void delete(String bucketName) throws ResourceException {

    storagePort.deleteBucket(bucketName);
  }
}
