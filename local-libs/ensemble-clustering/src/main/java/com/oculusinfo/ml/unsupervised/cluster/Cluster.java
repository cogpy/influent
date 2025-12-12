/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.unsupervised.cluster;

import com.oculusinfo.ml.Instance;
import com.oculusinfo.ml.feature.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
    private String id;
    private List<Instance> members = new ArrayList<Instance>();
    private Map<String, Feature> features = new HashMap<String, Feature>();

    public Cluster() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public Cluster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Instance> getMembers() {
        return members;
    }

    public void addMember(Instance instance) {
        members.add(instance);
    }

    public void removeMember(Instance instance) {
        members.remove(instance);
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

    public int size() {
        return members.size();
    }
}
