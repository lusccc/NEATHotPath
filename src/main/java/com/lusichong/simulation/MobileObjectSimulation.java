package com.lusichong.simulation;

import com.lusichong.util.Log;
import edu.gatech.lbs.core.query.LocationBasedQuery;
import edu.gatech.lbs.core.query.QueryKey;
import edu.gatech.lbs.sim.Simulation;
import edu.gatech.lbs.sim.agent.SimAgent;
import edu.gatech.lbs.sim.scheduling.SimEventQueue;
import edu.gatech.lbs.sim.scheduling.activity.ISimActivity;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lusichong on 2017/4/29 18:49.
 */
public class MobileObjectSimulation extends Simulation {

    private static final String TAG = MobileObjectSimulation.class.getSimpleName();

    @Override
    public void initSimulation() {
        eventQueue = new SimEventQueue();
        eventQueue.setLoadableSimEvents(getLoadableTraceSimEvents());

        agentIndex = new HashMap<Integer, List<SimAgent>>();
        queries = new HashMap<QueryKey, LocationBasedQuery>();

        simTime = simStartTime;
    }

    public boolean generateTrace(String traceConfig) {
        deleteLastTraceFile();
        loadConfiguration(traceConfig);
        initSimulation();
        ((LinkedList<ISimActivity>)simActivities).get(0).scheduleOn(this);
        Log.i(TAG, "trajectory generated at configs/traces/sim.m.trace.txt .");
        return true;

    }

    private void deleteLastTraceFile() {
        try {
            File file = new File("configs/traces");
            if (!file.exists()) {
                Log.e(TAG, "trace file not exists!");
            }
            File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
            Log.i(TAG, "last trace file delete success.");
        } catch (Exception e) {
            Log.e(TAG, "deleteLastTraceFile fail!");
            e.printStackTrace();
        }
    }
}
