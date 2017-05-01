package com.lusichong;

import com.lusichong.neat.clustering.NeatClustering;
import com.lusichong.neat.clustering.algorithm.FlowCluster;
import com.lusichong.neat.clustering.base.PointsOnSeg;
import com.lusichong.neat.clustering.base.Trajectory;
import com.lusichong.simulation.MobileObjectSimulation;
import com.lusichong.util.*;
import edu.gatech.lbs.core.vector.CartesianVector;
import edu.gatech.lbs.core.world.roadnet.RoadSegment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lusichong on 2017/4/29 20:34.
 */
public class LogicModel {

    private static final String TAG = LogicModel.class.getSimpleName();

    protected Collection<Trajectory> mTrajectories;

    private NeatClustering mNeatClustering = new NeatClustering(Const.BEIJING_MAP_CONFIG);


    public String generateTrajectory(int moCount) {
        Log.i(TAG, "generating trajectory ...");

        Utils.setSimulationXMLParams(moCount);
        MobileObjectSimulation simulation = new MobileObjectSimulation();
        simulation.initSimulation();
        simulation.generateTrace(Const.BEIJING_MAP_TRAJECTORY_CONFIG);

        TrajectoryDataImporter importer = new TrajectoryDataImporter();
        mTrajectories = importer.loadTrajectories(Const.TRAJECTORY_FILE);
        for (Trajectory trajectory : mTrajectories) {
            trajectory.generateRoute();
        }


        JSONArray trajsJson = new JSONArray();
        try {
            for (Trajectory t : mTrajectories) {
                JSONArray trajJson = new JSONArray();
                for (PointsOnSeg pos : t.getRoute()) {
                    RoadSegment seg = pos.getRoadSeg(mNeatClustering.getRoadmap());
                    CartesianVector[] points = seg.getGeometry().getPoints();
                    for (CartesianVector point : points) {
                        double[] coord = CoordConverter.convert(point);
                        JSONObject coordJson = new JSONObject();
                        coordJson.put("lng", coord[0]);
                        coordJson.put("lat", coord[1]);
                        trajJson.put(coordJson);
                    }
                }
                trajsJson.put(trajJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.i(TAG, "generating trajectory...success");
        return trajsJson.toString();
    }

    public String getBaseCluster() {

        File trajFile = new File(Const.TRAJECTORY_FILE);
        if (!trajFile.exists()) {
            Log.e(TAG, "please generate trajectory first!");
            return "-1";
        }

        Log.i(TAG, "getting base cluster...");

        mNeatClustering.loadTrajectories();
        mNeatClustering.formBaseClusters();

        ArrayList<PointsOnSeg> segList = new ArrayList<PointsOnSeg>(mNeatClustering.getTfragments());

        JSONArray baseClustersJson = new JSONArray();
        try {
            for (PointsOnSeg pos : segList) {
                JSONArray baseClusterJson = new JSONArray();
                RoadSegment seg = pos.getRoadSeg(mNeatClustering.getRoadmap());
                CartesianVector[] points = seg.getGeometry().getPoints();
                for (CartesianVector point : points) {
                    double[] coord = CoordConverter.convert(point);
                    JSONObject coordJson = new JSONObject();
                    coordJson.put("lng", coord[0]);
                    coordJson.put("lat", coord[1]);
                    baseClusterJson.put(coordJson);
                }
                baseClustersJson.put(baseClusterJson);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getting base cluster...success");


        return baseClustersJson.toString();

    }

    public String getFlowCluster() {

        if (mNeatClustering.isTFragmentsEmpty()) {
            Log.e(TAG, "please form base cluster first!");
            return "-1";
        }

        Log.i(TAG, "getting flow cluster...");

        mNeatClustering.computeFlows();

        List<FlowCluster> flows = mNeatClustering.getFlowClusters();

        JSONArray flowsJson = new JSONArray();
        try {
            for (FlowCluster fc : flows) {
                JSONArray flowJson = new JSONArray();
                for (PointsOnSeg pos : fc.getFlowClus()) {
                    RoadSegment seg = pos.getRoadSeg(mNeatClustering.getRoadmap());
                    CartesianVector[] points = seg.getGeometry().getPoints();
                    for (CartesianVector point : points) {
                        double[] coord = CoordConverter.convert(point);
                        JSONObject coordJson = new JSONObject();
                        coordJson.put("lng", coord[0]);
                        coordJson.put("lat", coord[1]);
                        flowJson.put(coordJson);
                    }
                }
                flowsJson.put(flowJson);
            }
            Log.i(TAG, "getting flow cluster...success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flowsJson.toString();
    }

    public void getSimulatedTrajectory() {

    }


}
