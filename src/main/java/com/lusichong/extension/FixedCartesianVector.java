package com.lusichong.extension;

import edu.gatech.lbs.core.vector.CartesianVector;

/**
 * Created by lusichong on 2017/4/29 11:36.
 */
public class FixedCartesianVector extends CartesianVector {
    public FixedCartesianVector(CartesianVector ori) {
        super(ori.getX(), ori.getY());
    }

    @Override
    public double getLongitude() {
        double lat = getLatitude() / 180 * Math.PI;
//        int mr = 6367449;
//        return (x / 1000) /
//                (Math.PI / 180 * mr * Math.cos(lat));


        return (x / 1000) /
                (111412.84 * Math.cos(lat) - 93.5 * Math.cos(3 * lat) + 0.118 * Math.cos(5 * lat));
    }

}
