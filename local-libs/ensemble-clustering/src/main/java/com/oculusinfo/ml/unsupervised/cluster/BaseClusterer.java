/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.unsupervised.cluster;

import com.oculusinfo.ml.DataSet;
import com.oculusinfo.ml.Instance;
import com.oculusinfo.ml.feature.Centroid;
import com.oculusinfo.ml.feature.Distance;
import com.oculusinfo.ml.feature.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseClusterer {
    protected Map<String, Class<? extends Centroid>> centroidTypes = new HashMap<String, Class<? extends Centroid>>();
    protected Map<String, Distance> distanceFunctions = new HashMap<String, Distance>();
    protected boolean useMultiThread;

    public BaseClusterer(boolean useMultiThread) {
        this.useMultiThread = useMultiThread;
    }

    public void registerFeatureType(String name, Class<? extends Centroid> centroidClass, Distance distance) {
        centroidTypes.put(name, centroidClass);
        distanceFunctions.put(name, distance);
    }

    public Cluster createCluster() {
        return new Cluster();
    }

    protected double computeDistance(Instance inst, Cluster cluster) {
        double totalDist = 0;
        for (Map.Entry<String, Feature> entry : inst.getFeatures().entrySet()) {
            String featureName = entry.getKey();
            Feature f1 = entry.getValue();
            Feature f2 = cluster.getFeature(featureName);
            Distance dist = distanceFunctions.get(featureName);
            if (dist != null && f2 != null) {
                totalDist += dist.distance(f1, f2);
            }
        }
        return totalDist;
    }

    public abstract ClusterResult doCluster(DataSet ds);

    public abstract ClusterResult doIncrementalCluster(DataSet ds, List<Cluster> existingClusters);

    public void terminate() {
        // Cleanup resources if needed
    }
}
