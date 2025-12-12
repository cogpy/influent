/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.spatial.distance;

import com.oculusinfo.ml.feature.Distance;
import com.oculusinfo.ml.feature.spatial.GeoSpatialFeature;

public class HaversineDistance extends Distance<GeoSpatialFeature> {
    private static final double EARTH_RADIUS_KM = 6371.0;

    public HaversineDistance(double weight) {
        super(weight);
    }

    @Override
    public double distance(GeoSpatialFeature f1, GeoSpatialFeature f2) {
        double lat1 = Math.toRadians(f1.getLatitude());
        double lat2 = Math.toRadians(f2.getLatitude());
        double lon1 = Math.toRadians(f1.getLongitude());
        double lon2 = Math.toRadians(f2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return weight * (EARTH_RADIUS_KM * c / EARTH_RADIUS_KM); // normalized
    }
}
