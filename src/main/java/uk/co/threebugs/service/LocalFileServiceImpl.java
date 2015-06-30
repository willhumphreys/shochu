package uk.co.threebugs.service;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LocalFileServiceImpl implements LocalFileService {

    private static final Logger LOG = getLogger(LocalFileServiceImpl.class);

    private Set<String> localFiles;

    public LocalFileServiceImpl() {
        localFiles = Sets.newHashSet();
    }

    @Override
    public Set<String> listLocalFiles() {
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