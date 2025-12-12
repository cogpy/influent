/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.string.centroid;

import com.oculusinfo.ml.feature.Centroid;
import com.oculusinfo.ml.feature.string.StringFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringMedianCentroid extends Centroid<StringFeature> {
    private List<String> values = new ArrayList<String>();
    private Map<String, Integer> counts = new HashMap<String, Integer>();

    public StringMedianCentroid(String name) {
        super(name);
    }

    @Override
    public void add(StringFeature feature) {
        if (feature.getValue() != null) {
            values.add(feature.getValue());
            Integer count = counts.get(feature.getValue());
            counts.put(feature.getValue(), count == null ? 1 : count + 1);
        }
    }

    @Override
    public void remove(StringFeature feature) {
        if (feature.getValue() != null) {
            values.remove(feature.getValue());
            Integer count = counts.get(feature.getValue());
            if (count != null && count > 1) {
                counts.put(feature.getValue(), count - 1);
            } else {
                counts.remove(feature.getValue());
            }
        }
    }

    @Override
    public StringFeature getCentroid() {
        StringFeature centroid = new StringFeature(name);
        String mostCommon = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        centroid.setValue(mostCommon);
        return centroid;
    }

    @Override
    public void reset() {
        values.clear();
        counts.clear();
    }
}
