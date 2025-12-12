/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.unsupervised.cluster.kmeans;

import com.oculusinfo.ml.DataSet;
import com.oculusinfo.ml.Instance;
import com.oculusinfo.ml.feature.Feature;
import com.oculusinfo.ml.unsupervised.cluster.BaseClusterer;
import com.oculusinfo.ml.unsupervised.cluster.Cluster;
import com.oculusinfo.ml.unsupervised.cluster.ClusterResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans extends BaseClusterer {
    private int k;
    private int maxIterations;
    private Random random = new Random();

    public KMeans(int k, int maxIterations, boolean useMultiThread) {
        super(useMultiThread);
        this.k = k;
        this.maxIterations = maxIterations;
    }

    @Override
    public ClusterResult doCluster(DataSet ds) {
        List<Cluster> clusters = initializeClusters(ds);
        return runKMeans(ds, clusters);
    }

    @Override
    public ClusterResult doIncrementalCluster(DataSet ds, List<Cluster> existingClusters) {
        List<Cluster> clusters = new ArrayList<Cluster>(existingClusters);
        return runKMeans(ds, clusters);
    }

    private List<Cluster> initializeClusters(DataSet ds) {
        List<Cluster> clusters = new ArrayList<Cluster>();
        List<Instance> instances = ds.getInstances();

        if (instances.isEmpty()) return clusters;

        // Randomly select k instances as initial centroids
        int numClusters = Math.min(k, instances.size());
        List<Integer> selected = new ArrayList<Integer>();

        while (selected.size() < numClusters) {
            int idx = random.nextInt(instances.size());
            if (!selected.contains(idx)) {
                selected.add(idx);
                Cluster cluster = createCluster();
                Instance inst = instances.get(idx);
                // Copy features as initial centroid
                for (Map.Entry<String, Feature> entry : inst.getFeatures().entrySet()) {
                    cluster.addFeature(entry.getValue());
                }
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    private ClusterResult runKMeans(DataSet ds, List<Cluster> clusters) {
        if (clusters.isEmpty()) {
            clusters = initializeClusters(ds);
        }

        for (int iter = 0; iter < maxIterations; iter++) {
            // Clear previous members
            for (Cluster c : clusters) {
                c.getMembers().clear();
            }

            // Assign each instance to nearest cluster
            for (Instance inst : ds) {
                Cluster nearest = findNearestCluster(inst, clusters);
                if (nearest != null) {
                    nearest.addMember(inst);
                }
            }

            // Update centroids (simplified - just use first member's features)
            for (Cluster c : clusters) {
                if (!c.getMembers().isEmpty()) {
                    Instance first = c.getMembers().get(0);
                    c.getFeatures().clear();
                    for (Map.Entry<String, Feature> entry : first.getFeatures().entrySet()) {
                        c.addFeature(entry.getValue());
                    }
                }
            }
        }

        ClusterResult result = new ClusterResult();
        for (Cluster c : clusters) {
            if (!c.getMembers().isEmpty()) {
                result.add(c);
            }
        }
        return result;
    }

    private Cluster findNearestCluster(Instance inst, List<Cluster> clusters) {
        Cluster nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Cluster c : clusters) {
            double dist = computeDistance(inst, c);
            if (dist < minDist) {
                minDist = dist;
                nearest = c;
            }
        }
        return nearest;
    }
}
