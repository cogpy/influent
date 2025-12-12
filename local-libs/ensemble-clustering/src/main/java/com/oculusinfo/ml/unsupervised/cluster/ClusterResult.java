/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.unsupervised.cluster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClusterResult implements Iterable<Cluster> {
    private List<Cluster> clusters = new ArrayList<Cluster>();

    public void add(Cluster cluster) {
        clusters.add(cluster);
    }

    public Cluster get(int index) {
        return clusters.get(index);
    }

    public int size() {
        return clusters.size();
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    @Override
    public Iterator<Cluster> iterator() {
        return clusters.iterator();
    }
}
