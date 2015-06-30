package uk.co.threebugs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.threebugs.service.BucketServiceImpl;
import uk.co.threebugs.service.LocalFileServiceImpl;

import java.util.Set;

@Controller
public class DataController {

    private final LocalFileServiceImpl localFileService;
    private final BucketServiceImpl bucketService;

    @Autowired
    public DataController(LocalFileServiceImpl localFileService, BucketServiceImpl bucketService) {
        this.localFileService = localFileService;
        this.bucketService = bucketService;
    }

    @RequestMapping("/localfiles")
    public @ResponseBody
    Set<String> getLocalFiles() {
        return localFileService.listLocalFiles();
    }
}
