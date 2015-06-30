package uk.co.threebugs;

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

    @RequestMapping("/localFiles")
    public @ResponseBody
    Set<String> getLocalFiles() {
        return localFileService.listLocalFiles();
    }
}
