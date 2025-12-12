/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.unsupervised.cluster.dpmeans;

import com.oculusinfo.ml.DataSet;
import com.oculusinfo.ml.Instance;
import com.oculusinfo.ml.feature.Feature;
import com.oculusinfo.ml.unsupervised.cluster.BaseClusterer;
import com.oculusinfo.ml.unsupervised.cluster.Cluster;
import com.oculusinfo.ml.unsupervised.cluster.ClusterResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DPMeans extends BaseClusterer {
    private int maxIterations;
    private double threshold = 1.0;

    public DPMeans(int maxIterations, boolean useMultiThread) {
        super(useMultiThread);
        this.maxIterations = maxIterations;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getThreshold() {
        return threshold;
    }

    @Override
    public ClusterResult doCluster(DataSet ds) {
        List<Cluster> clusters = new ArrayList<Cluster>();
        return runDPMeans(ds, clusters);
    }

    @Override
    public ClusterResult doIncrementalCluster(DataSet ds, List<Cluster> existingClusters) {
        List<Cluster> clusters = new ArrayList<Cluster>(existingClusters);
        return runDPMeans(ds, clusters);
    }

    private ClusterResult runDPMeans(DataSet ds, List<Cluster> clusters) {
        for (int iter = 0; iter < maxIterations; iter++) {
            boolean changed = false;

            // Clear previous members
            for (Cluster c : clusters) {
                c.getMembers().clear();
            }

            // Assign each instance to nearest cluster or create new one
            for (Instance inst : ds) {
                Cluster nearest = null;
                double minDist = Double.MAX_VALUE;

                for (Cluster c : clusters) {
                    double dist = computeDistance(inst, c);
                    if (dist < minDist) {
                        minDist = dist;
                        nearest = c;
                    }
                }

                if (nearest == null || minDist > threshold) {
                    // Create new cluster
                    Cluster newCluster = createCluster();
                    for (Map.Entry<String, Feature> entry : inst.getFeatures().entrySet()) {
                        newCluster.addFeature(entry.getValue());
                    }
                    newCluster.addMember(inst);
                    clusters.add(newCluster);
                    changed = true;
                } else {
                    nearest.addMember(inst);
                }
            }

            // Update centroids
            for (Cluster c : clusters) {
                if (!c.getMembers().isEmpty()) {
                    Instance first = c.getMembers().get(0);
                    c.getFeatures().clear();
                    for (Map.Entry<String, Feature> entry : first.getFeatures().entrySet()) {
                        c.addFeature(entry.getValue());
                    }
                }
            }

            if (!changed && iter > 0) break;
        }

        ClusterResult result = new ClusterResult();
        for (Cluster c : clusters) {
            if (!c.getMembers().isEmpty()) {
                result.add(c);
            }
        }
        return result;
    }
}
