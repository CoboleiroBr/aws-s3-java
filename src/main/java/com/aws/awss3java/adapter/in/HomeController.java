package com.aws.awss3java.adapter.in;

import com.aws.awss3java.adapter.dto.ObjectRequest;
import com.aws.awss3java.application.exception.ResourceException;
import com.aws.awss3java.application.ports.in.BucketPort;
import com.aws.awss3java.adapter.dto.BucketRequest;
import com.aws.awss3java.application.ports.in.ObjectPort;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

  private final BucketPort bucketPort;
  private final ObjectPort objectPort;

  @PostMapping(value = "/bucket", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createBucket(@Valid @RequestBody BucketRequest bucketRequest)
      throws ResourceException {

    bucketPort.create(bucketRequest.getBucketName());
  }

  @DeleteMapping(value = "/bucket", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void deleteBucket(@Valid @RequestBody BucketRequest bucketRequest)
      throws ResourceException {

    bucketPort.delete(bucketRequest.getBucketName());
  }


  @PutMapping(value = "/object", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void putObject(@Valid ObjectRequest objectRequest)
      throws ResourceException {

    objectPort.put(objectRequest.getBucketName(), objectRequest.getFileObjectKeyName(),
        objectRequest.getFileName(), objectRequest.getFile());
  }
}
