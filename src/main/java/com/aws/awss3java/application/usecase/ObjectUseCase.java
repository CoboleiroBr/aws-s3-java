package com.aws.awss3java.application.usecase;

import com.aws.awss3java.application.exception.ResourceException;
import com.aws.awss3java.application.ports.in.BucketPort;
import com.aws.awss3java.application.ports.in.ObjectPort;
import com.aws.awss3java.application.ports.out.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectUseCase implements ObjectPort {

  private final StoragePort storagePort;

  @Override
  public void put(String bucketName, String objectFileName, String fileName, MultipartFile file)
      throws ResourceException {

    storagePort.putObject(bucketName, objectFileName, fileName, file);
  }
}
