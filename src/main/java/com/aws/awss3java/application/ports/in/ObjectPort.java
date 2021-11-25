package com.aws.awss3java.application.ports.in;

import com.aws.awss3java.application.exception.ResourceException;
import org.springframework.web.multipart.MultipartFile;

public interface ObjectPort {

  void put(String bucketName, String objectFileName, String fileName, MultipartFile file)
      throws ResourceException;
}
