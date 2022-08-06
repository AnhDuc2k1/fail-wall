package org.aibles.failwall.awss3.controller;

import org.aibles.failwall.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.failwall.awss3.dto.res.S3PreSignedUrlResDto;
import org.aibles.failwall.awss3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {

    private final S3Service s3Service;

    @Autowired
    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PutMapping("/upload_presigned_url")
    public ResponseEntity<S3PreSignedUrlResDto> uploadPreSignedUrl(@Valid @RequestBody S3PreSignedUrlReqDto s3PreSignedUrlReqDto){
            return new ResponseEntity<>(s3Service.createUploadPreSignedUrl(s3PreSignedUrlReqDto), HttpStatus.OK);
    }

    @GetMapping("/download_presigned_url")
    public ResponseEntity<S3PreSignedUrlResDto> downloadPreSignedUrl(@RequestParam(name = "s3ObjectKey") String s3ObjectKey){
            return new ResponseEntity<>(s3Service.createDownloadPreSignedUrl(s3ObjectKey), HttpStatus.OK);
    }

}
