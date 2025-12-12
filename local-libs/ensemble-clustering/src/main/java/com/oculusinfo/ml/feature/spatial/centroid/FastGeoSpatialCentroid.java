/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.spatial.centroid;

import com.oculusinfo.ml.feature.Centroid;
import com.oculusinfo.ml.feature.spatial.GeoSpatialFeature;

import java.util.ArrayList;
import java.util.List;

public class FastGeoSpatialCentroid extends Centroid<GeoSpatialFeature> {
    private List<double[]> coordinates = new ArrayList<double[]>();

    public FastGeoSpatialCentroid(String name) {
        super(name);
    }

    @Override
    public void add(GeoSpatialFeature feature) {
        coordinates.add(new double[]{feature.getLatitude(), feature.getLongitude()});
    }

    @Override
    public void remove(GeoSpatialFeature feature) {
        for (int i = 0; i < coordinates.size(); i++) {
            double[] coord = coordinates.get(i);
            if (coord[0] == feature.getLatitude() && coord[1] == feature.getLongitude()) {
                coordinates.remove(i);
                break;
            }
        }
    }

    @Override
    public GeoSpatialFeature getCentroid() {
        GeoSpatialFeature centroid = new GeoSpatialFeature(name);
        if (coordinates.isEmpty()) {
            centroid.setValue(0, 0);
            return centroid;
        }

        double sumLat = 0, sumLon = 0;
        for (double[] coord : coordinates) {
            sumLat += coord[0];
            sumLon += coord[1];
        }
        centroid.setValue(sumLat / coordinates.size(), sumLon / coordinates.size());
        return centroid;
    }

    @Override
    public void reset() {
        coordinates.clear();
    }
}
