package com.aws.awss3java.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BucketException extends RuntimeException {

  private final String message;
}
