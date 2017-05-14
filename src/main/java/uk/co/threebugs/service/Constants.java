package uk.co.threebugs.service;

import com.amazonaws.regions.Regions;

import java.nio.file.Path;
import java.nio.file.Paths;

class Constants {
    static final Path LIVE_DATA_PATH = Paths.get("/mochi-graphs");
    static final Path TICK_DATA_PATH = Paths.get("/mochi-data");
    static final String LIVE_DATA_BUCKET = "mochi-graphs";
    static final String TICK_DATA_BUCKET = "mochi-data2";
    static final Regions FALL_BACK_REGION = Regions.US_EAST_1;
}
