package org.aibles.failwall.awss3.service;

import org.aibles.failwall.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.failwall.awss3.dto.res.S3PreSignedUrlResDto;

public interface S3Service {
    S3PreSignedUrlResDto createUploadPreSignedUrl(S3PreSignedUrlReqDto s3PreSignedUrlReqDto);
    S3PreSignedUrlResDto createDownloadPreSignedUrl(String s3ObjectKey);
}
