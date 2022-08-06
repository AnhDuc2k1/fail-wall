package org.aibles.failwall.awss3.service.iml;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.aibles.failwall.awss3.dto.req.S3PreSignedUrlReqDto;
import org.aibles.failwall.awss3.dto.res.S3PreSignedUrlResDto;
import org.aibles.failwall.awss3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@Service
public class S3ServiceIml implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String S3_BUCKET;

    @Autowired
    public S3ServiceIml(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public S3PreSignedUrlResDto createUploadPreSignedUrl(S3PreSignedUrlReqDto s3PreSignedUrlReqDto) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(S3_BUCKET,
                generateS3ObjectKey(s3PreSignedUrlReqDto.getFileName()))
                .withMethod(HttpMethod.PUT)
                .withExpiration(generatePreSignedUrlExpiration());

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return new S3PreSignedUrlResDto(url.toString());
    }

    @Override
    public S3PreSignedUrlResDto createDownloadPreSignedUrl(String s3ObjectKey) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(S3_BUCKET, s3ObjectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(generatePreSignedUrlExpiration());

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return new S3PreSignedUrlResDto(url.toString());
    }

    private String generateS3ObjectKey(final String fileName){
        return String.format("%s_%s",
                Instant.now(Clock.systemDefaultZone()).toString(),
                fileName.replace(" ", "_"));
    }

    private Date generatePreSignedUrlExpiration(){
        Date expiration = new Date();
        long milliSeconds = expiration.getTime();
        milliSeconds += 3_600_600; //1 hour
        expiration.setTime(milliSeconds);

        return expiration;
    }
}
