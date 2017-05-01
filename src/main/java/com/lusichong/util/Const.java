package com.lusichong.util;


import com.google.common.base.Strings;

public class Const {

    public static final String BEIJING_MAP_CONFIG = "configs/beijing-mapconfig-shp.xml";

    public static final String BEIJING_MAP_TRAJECTORY_CONFIG = "configs/beijing-tracegen-shp.xml";

    public static final String TRAJECTORY_FILE = "configs/traces/sim.m.trace.txt";

    // change the roadmap in the config file to use the correct map associated with traceFile
    public static String configFile = (!Strings.isNullOrEmpty(System.getProperty("configFile"))) ? System
            .getProperty("configFile") : "configs/beijing-mapconfig-shp.xml";

    public static String traceFile = (!Strings.isNullOrEmpty(System.getProperty("traceFile"))) ? System
            .getProperty("traceFile") : "/Users/lusichong/Desktop/gt-mobisim/configs/traces/sim.m.trace.txt";

    public static String outputClusteringResult = (!Strings.isNullOrEmpty(System.getProperty("outputClusteringResult"))) ? System
            .getProperty("outputClusteringResult") : "configs/output/neat-clusters.txt";
}
