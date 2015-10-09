package uk.co.threebugs.service;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LocalFileServiceImpl implements LocalFileService {

    private static final Logger LOG = getLogger(LocalFileServiceImpl.class);

    @Override
    public Set<String> listLocalFiles(final Path tickDataPath, boolean liveData) {

        Set<String> localFiles = Sets.newHashSet();

        LOG.info("List local files");

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tickDataPath)) {
            for (Path path : directoryStream) {
                final Path fileName = path.getFileName();

                if (fileName.toString().endsWith(".csv") || liveData) {
                    LOG.info(fileName.toString());
                    localFiles.add(fileName
                            .toString());
                }


            }
        } catch (IOException ex) {
            LOG.error("Failed to list files", ex);

        }

        return localFiles;
    }
}
