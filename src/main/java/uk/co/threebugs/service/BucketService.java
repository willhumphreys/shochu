package uk.co.threebugs.service;

import com.amazonaws.services.s3.AmazonS3;

import java.util.Set;

public interface BucketService {
    Set<String> listRemoteFiles(AmazonS3 s3, String bucketName);

    void createBucket(AmazonS3 s3, String bucketName);

    AmazonS3 loginAWS();
}
