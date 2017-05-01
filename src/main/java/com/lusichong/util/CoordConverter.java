package com.lusichong.util;


import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import edu.gatech.lbs.core.vector.CartesianVector;
import org.cts.CRSFactory;
import org.cts.crs.GeodeticCRS;
import org.cts.op.CoordinateOperation;
import org.cts.op.CoordinateOperationFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import java.util.List;

/**
 * Created by lusichong on 2017/4/29 14:53.
 */
public class CoordConverter {

    private static final String TAG = CoordConverter.class.getSimpleName();

    public static double[] convert(CartesianVector point) {
        double unit = 1e6 * 40075 / 360;

        return new double[] {
                (point.getX() / unit) + .0062,
                (point.getY() / unit) + .0015
        };
//        Point p = convertCRS(point.getX() / unit,
//                point.getY() / unit);
//        return new double[]{
//                p.getY(), p.getX()
//        };
    }

    public static Point convertCRS(double x, double y) {
        try {
            GeometryFactory gf = new GeometryFactory();
            Coordinate c = new Coordinate(x, y);

            Point p = gf.createPoint(c);

            CoordinateReferenceSystem utmCrs = CRS.decode("EPSG:3857");
            MathTransform mathTransform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, utmCrs, false);
            Point p1 = (Point) JTS.transform(p, mathTransform);

            Log.i(TAG, "convertCRS ori:" + c + " after:" + p1.getCoordinate());

            return p1;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Thread.dumpStack();
        }
        return null;
    }

    public static void convertCRS2(double x, double y) {
        try {
            CRSFactory crsFactory = new CRSFactory();
            org.cts.crs.CoordinateReferenceSystem wgs84 = crsFactory.getCRS("EPSG:4326");
            org.cts.crs.CoordinateReferenceSystem mercator = crsFactory.getCRS("EPSG:3857");
            //CoordinateReferenceSystem mercator = cRSFactory.getCRS("ESRI:102100");
            GeodeticCRS target = (GeodeticCRS) wgs84;
            GeodeticCRS source = (GeodeticCRS) mercator;
            List<CoordinateOperation> coordOps = CoordinateOperationFactory.createCoordinateOperations(source, target);

            if (coordOps.size() != 0) {
                for (CoordinateOperation op : coordOps) {
                    double[] newP = op.transform(new double[]{x, y});
                    Log.i(TAG, "convertCRS2 ori: x:" + x + "y:" +y +  " after: x:" + newP[0] + " y:" + newP[1]);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public double[] transform(GeodeticCRS sourceCRS, GeodeticCRS targetCRS, double[] inputPoint)
//            throws IllegalCoordinateException, CoordinateOperationException {
//        List<CoordinateOperation> ops;
//        int tot, subtot;
//        ops = CoordinateOperationFactory.createCoordinateOperations(sourceCRS, targetCRS);
//        tot = ops.size();
//        //for (CoordinateOperation  op : ops) System.out.println("   " + op.getName());
//        if (sourceCRS.getDatum() == GeodeticDatum.WGS84 || targetCRS.getDatum() == GeodeticDatum.WGS84) {
//            ops = CoordinateOperationFactory.excludeFilter(ops, FrenchGeocentricNTF2RGF.class);
//            ops = CoordinateOperationFactory.excludeFilter(ops, NTv2GridShiftTransformation.class);
//        }
//        // If source CRS comes from the EPSG registry and is not a CompoundCRS,
//        // we use BursaWolf or translation rather than GridBasedTransformation,
//        // even if a GridBasef Transformation is available (precise transformation
//        // may be available because we also read IGNF registry and precise
//        // transformations have been stored in GeodeticDatum objects.
//        else if (sourceCRS.getIdentifier().getAuthorityName().equals("EPSG") &&
//                !(sourceCRS instanceof CompoundCRS) && !(targetCRS instanceof CompoundCRS)) {
//            ops = CoordinateOperationFactory.excludeFilter(ops, GridBasedTransformation.class);
//        }
//        subtot = ops.size();
//        if (!ops.isEmpty()) {
//            CoordinateOperation op = CoordinateOperationFactory.getMostPrecise(ops);
//            if (true) {
//                System.out.println("Source " + sourceCRS);
//                System.out.println("Target " + targetCRS);
//                System.out.println(tot + " transformations found, " + subtot + " retained");
//                System.out.println("Used transformation (" + op.getPrecision() + ") : " + op);
//
//                if (ops.size() > 1) {
//                    for (CoordinateOperation oop : ops) {
//                        //System.out.println("   a transformation with precision (" + oop.getPrecision() + ") : " + oop);
//                        System.out.println("   other transformation : precision = " + oop.getPrecision());
//                    }
//                }
//            }
//            double[] input = new double[inputPoint.length];
//            System.arraycopy(inputPoint, 0, input, 0, inputPoint.length);
//            return op.transform(input);
//        } else {
//            System.out.println("No transformation found from " + sourceCRS + " to " + targetCRS);
//            return new double[]{0.0d, 0.0d, 0.0d};
//        }
//    }
}
