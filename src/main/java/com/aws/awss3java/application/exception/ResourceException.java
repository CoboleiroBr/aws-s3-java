package com.aws.awss3java.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResourceException extends Exception {

  private final String message;
}
