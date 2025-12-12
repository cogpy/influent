/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml;

import java.util.HashMap;
import java.util.Map;

import com.oculusinfo.ml.feature.Feature;

public class Instance {
    private String id;
    private Map<String, Feature> features = new HashMap<String, Feature>();

    public Instance() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public Instance(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addFeature(Feature feature) {
        features.put(feature.getName(), feature);
    }

    public Feature getFeature(String name) {
        return features.get(name);
    }

    public Map<String, Feature> getFeatures() {
        return features;
    }
}
