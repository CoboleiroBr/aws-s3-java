package com.aws.awss3java.adapter.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectRequest {

  @NotEmpty
  private String bucketName;

  @NotEmpty
  private String fileObjectKeyName;

  @NotEmpty
  private String fileName;

  @NotNull
  private MultipartFile file;
}
