package uk.co.threebugs.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class BucketServiceImpl implements BucketService {

    private static final Logger LOG = getLogger(BucketServiceImpl.class);

    private AmazonS3 s3;


    @Override
    public Set<String> listRemoteFiles(AmazonS3 s3, String bucketName) {

        Set<String> remoteFiles = Sets.newHashSet();

        LOG.info("List remote files " + bucketName);
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName));

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");

            remoteFiles.add(objectSummary.getKey());
        }

        return remoteFiles;
    }

    @Override
    public void createBucket(AmazonS3 s3, String bucketName) {
        try {
            s3.listObjects(new ListObjectsRequest("tickdata-matcha", null, null, null, 0));
        } catch (Exception e) {
            LOG.warn("No tick-data bucket. Going to create one.");
            s3.createBucket(bucketName);
        }
    }

    @Override
    public AmazonS3 loginAWS() {
    /*
     * The ProfileCredentialsProvider will return your [default]
     * credential profile by reading from the credentials file located at
     * (~/.aws/credentials).
     */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        if(s3 == null) {

            s3 = new AmazonS3Client(credentials);
            Region usWest2 = Region.getRegion(Regions.US_WEST_2);
            s3.setRegion(usWest2);

        }



        return s3;
    }
}