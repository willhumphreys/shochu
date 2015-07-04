package uk.co.threebugs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.common.collect.Sets;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class DataSyncServiceImpl implements DataSyncService {

    private static final Logger LOG = getLogger(DataSyncServiceImpl.class);
    private final BucketService bucketService;
    private final LocalFileService localFileService;

    @Autowired
    public DataSyncServiceImpl(BucketService bucketService, LocalFileService localFileService) {
        this.bucketService = bucketService;
        this.localFileService = localFileService;
    }

    @Override
    public void refresh() {
        LOG.info("Refreshing");
        AmazonS3 s3 = bucketService.loginAWS();


        final String bucketName = "tickdata-matcha";
        bucketService.createBucket(s3, bucketName);

        final Set<String> remoteFiles = bucketService.listRemoteFiles(s3, bucketName);

        final Set<String> localFiles = localFileService.listLocalFiles(Paths.get("/tickdata"));

        upload(s3, bucketName, remoteFiles, localFiles);


        download(s3, bucketName, remoteFiles, localFiles);


    }

    private void download(AmazonS3 s3, String bucketName, Set<String> remoteFiles, Set<String> localFiles) {
        final Sets.SetView<String> missingLocalFiles = Sets.difference(remoteFiles, localFiles);

        LOG.info("Missing local files {}", missingLocalFiles.size());

        for (String missingLocalFile : missingLocalFiles) {


            final S3Object result = s3.getObject(bucketName, missingLocalFile);

            File targetFile = Paths.get("/tickdata", missingLocalFile)
                    .toFile();

            try {
                FileUtils.copyInputStreamToFile(result.getObjectContent(), targetFile);
                LOG.info("Downloaded {}", targetFile);
            } catch (IOException e) {
                LOG.error(String.format("Failed to persist %s", missingLocalFile), e);
            }
        }
    }

    private void upload(AmazonS3 s3, String bucketName, Set<String> remoteFiles, Set<String> localFiles) {
        final Sets.SetView<String> missingRemoteFiles = Sets.difference(localFiles, remoteFiles);

        LOG.info("Missing remote files {}", missingRemoteFiles.size());

        final TransferManager tm = new TransferManager(s3);
        for (String missingRemoteFile : missingRemoteFiles) {

            final Path missingRemotePath = Paths.get("/tickdata", missingRemoteFile);


            LOG.info("Uploading {}", missingRemotePath);

            // final PutObjectResult result = simpleUpload(s3, bucketName, missingRemoteFile, missingRemotePath);

            //LOG.info("Uploaded {}", result.getETag());

            final Upload upload = uploadMultiPart(bucketName, missingRemoteFile, missingRemotePath, tm);


        }
    }

    private PutObjectResult simpleUpload(AmazonS3 s3, String bucketName, String missingRemoteFile, Path missingRemotePath) {
        return s3.putObject(new PutObjectRequest(bucketName, missingRemoteFile,
                missingRemotePath.toFile()));
    }

    private Upload uploadMultiPart(String bucketName, String fileName, Path filePath, final TransferManager tm) {

        // TransferManager processes all transfers asynchronously,
        // so this call will return immediately.
        Upload upload = tm.upload(bucketName, fileName, filePath.toFile());
        //      System.out.println("Hello2");

//        try {
//            // Or you can block and wait for the upload to finish
//            upload.waitForCompletion();
//            System.out.println("Upload complete.");
//        } catch (AmazonClientException amazonClientException) {
//            System.out.println("Unable to upload file, upload was aborted.");
//            amazonClientException.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return upload;

    }
}
