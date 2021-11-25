package com.aws.awss3java.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultReturn {

  @JsonProperty("statusCode")
  private String statusCode;

  @JsonProperty("message")
  private String message;
}