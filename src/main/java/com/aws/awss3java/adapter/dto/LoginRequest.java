package com.aws.awss3java.adapter.dto;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

  @NotEmpty
  private String clientId;

  @NotEmpty
  private String passwordId;

  @NotEmpty
  private String tokenId;
}
