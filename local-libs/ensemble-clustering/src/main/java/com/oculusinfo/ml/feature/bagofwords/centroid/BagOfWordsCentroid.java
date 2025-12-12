/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.bagofwords.centroid;

import com.oculusinfo.ml.feature.Centroid;
import com.oculusinfo.ml.feature.bagofwords.BagOfWordsFeature;

import java.util.HashMap;
import java.util.Map;

public class BagOfWordsCentroid extends Centroid<BagOfWordsFeature> {
    private Map<String, Integer> aggregatedCounts = new HashMap<String, Integer>();
    private int count = 0;

    public BagOfWordsCentroid(String name) {
        super(name);
    }

    @Override
    public void add(BagOfWordsFeature feature) {
        for (String word : feature.getWords()) {
            int wordCount = feature.getCount(word);
            Integer current = aggregatedCounts.get(word);
            aggregatedCounts.put(word, current == null ? wordCount : current + wordCount);
        }
        count++;
    }

    @Override
    public void remove(BagOfWordsFeature feature) {
        for (String word : feature.getWords()) {
            int wordCount = feature.getCount(word);
            Integer current = aggregatedCounts.get(word);
            if (current != null) {
                int newCount = current - wordCount;
                if (newCount <= 0) {
                    aggregatedCounts.remove(word);
                } else {
                    aggregatedCounts.put(word, newCount);
                }
            }
        }
        count = Math.max(0, count - 1);
    }

    @Override
    public BagOfWordsFeature getCentroid() {
        BagOfWordsFeature centroid = new BagOfWordsFeature(name);
        if (count > 0) {
            for (Map.Entry<String, Integer> entry : aggregatedCounts.entrySet()) {
                centroid.setCount(entry.getKey(), entry.getValue() / count);
            }
        }
        return centroid;
    }

    @Override
    public void reset() {
        aggregatedCounts.clear();
        count = 0;
    }
}
