package uk.co.threebugs;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.threebugs.service.BucketService;
import uk.co.threebugs.service.LocalFileService;

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

    @RequestMapping("/localfiles")
    public @ResponseBody
    Set<String> getLocalFiles() {
        return localFileService.listLocalFiles();
    }

    @RequestMapping("/remotefiles")
    public @ResponseBody
    Set<String> getRemoteFiles() {
        final AmazonS3 amazonS3 = bucketService.loginAWS();
        return bucketService.listRemoteFiles(amazonS3, "tickdata-matcha");
    }
}
