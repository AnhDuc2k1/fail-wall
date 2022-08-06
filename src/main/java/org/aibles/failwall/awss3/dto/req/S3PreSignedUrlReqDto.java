package org.aibles.failwall.awss3.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class S3PreSignedUrlReqDto {

    @NotBlank
    private String fileName;
}
