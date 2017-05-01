package com.lusichong.neat.clustering.algorithm;


import edu.gatech.lbs.core.world.roadnet.RoadMap;

import java.util.Comparator;


public class FlowClusterComparator implements Comparator<FlowCluster> {
    protected RoadMap roadmap;


    public FlowClusterComparator(RoadMap roadmap) {
        this.roadmap = roadmap;
    }

    public int compare(FlowCluster sc0, FlowCluster sc1) {

        if (sc0.getLength(roadmap) < sc1.getLength(roadmap)) return -1;
        else if (sc0.getLength(roadmap) > sc1.getLength(roadmap)) return 1;
        else return 0;
    }

}
