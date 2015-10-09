package uk.co.threebugs.service;

import java.nio.file.Path;
import java.util.Set;

public interface LocalFileService {
    Set<String> listLocalFiles(final Path tickDataPath, boolean liveData);
}
