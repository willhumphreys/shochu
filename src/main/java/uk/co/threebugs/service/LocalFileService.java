package uk.co.threebugs.service;

import com.google.common.collect.Sets;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class LocalFileService {

    private static final Logger LOG = getLogger(LocalFileService.class);

    private Set<String> localFiles;

    public LocalFileService() {
        localFiles = Sets.newHashSet();
    }

    Set<String> listLocalFiles() {
        LOG.info("List local files");
        final Path tickDataPath = Paths.get("/tickdata");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tickDataPath)) {
            for (Path path : directoryStream) {
                LOG.info(path.getFileName().toString());
                localFiles.add(path.getFileName().toString());

            }
        } catch (IOException ex) {
            LOG.error("Failed to list files", ex);

        }

        return localFiles;
    }
}