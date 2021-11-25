package com.aws.awss3java.adapter.in.advice;

import com.aws.awss3java.adapter.dto.DefaultReturn;
import com.aws.awss3java.application.exception.BucketException;
import com.aws.awss3java.application.exception.ResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(BucketException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public DefaultReturn handleBucketException(Exception e) {

    return new DefaultReturn(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), e.getMessage());
  }

  @ExceptionHandler(ResourceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public DefaultReturn handleExistentResourceException(Exception e) {

    return new DefaultReturn(String.valueOf(HttpStatus.BAD_REQUEST), e.getMessage());
  }
}