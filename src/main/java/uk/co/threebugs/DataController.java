package uk.co.threebugs;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.threebugs.service.BucketService;
import uk.co.threebugs.service.Constants;
import uk.co.threebugs.service.LocalFileService;

import java.nio.file.Paths;
import java.util.Set;

@Controller
public class DataController {

    private final LocalFileService localFileService;
    private final BucketService bucketService;

    @Autowired
    public DataController(LocalFileService localFileService, BucketService bucketService) {
        this.localFileService = localFileService;
        this.bucketService = bucketService;
    }

    @RequestMapping("/localfiles/{rootDir}")
    public @ResponseBody Set<String> getLocalFiles(@PathVariable("rootDir") String rootDir) {

        boolean liveData = false;
        if(rootDir.equals(Constants.LIVE_DATA_BUCKET)) {
            liveData = true;
        }

        return localFileService.listLocalFiles(Paths.get("/" + rootDir), liveData);
    }

    @RequestMapping("/remotefiles/{bucket}")
    public @ResponseBody Set<String> getRemoteFiles(@PathVariable("bucket") String bucket) {
        final AmazonS3 amazonS3 = bucketService.loginAWS();
        return bucketService.listRemoteFiles(amazonS3, bucket);
    }
}
