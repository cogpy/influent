/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.bagofwords.distance;

import com.oculusinfo.ml.feature.Distance;
import com.oculusinfo.ml.feature.bagofwords.BagOfWordsFeature;

import java.util.HashSet;
import java.util.Set;

public class CosineDistance extends Distance<BagOfWordsFeature> {

    public CosineDistance(double weight) {
        super(weight);
    }

    @Override
    public double distance(BagOfWordsFeature f1, BagOfWordsFeature f2) {
        Set<String> allWords = new HashSet<String>();
        allWords.addAll(f1.getWords());
        allWords.addAll(f2.getWords());

        if (allWords.isEmpty()) return 0;

        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (String word : allWords) {
            int c1 = f1.getCount(word);
            int c2 = f2.getCount(word);
            dotProduct += c1 * c2;
            norm1 += c1 * c1;
            norm2 += c2 * c2;
        }

        if (norm1 == 0 || norm2 == 0) return 1;

        double cosine = dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
        return weight * (1 - cosine);
    }
}
