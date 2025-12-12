/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.spatial;

import com.oculusinfo.ml.feature.Feature;

public class GeoSpatialFeature extends Feature {
    private double latitude;
    private double longitude;

    public GeoSpatialFeature(String name) {
        super(name);
    }

    public void setValue(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
