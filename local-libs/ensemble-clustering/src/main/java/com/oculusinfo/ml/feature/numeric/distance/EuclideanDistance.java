/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.numeric.distance;

import com.oculusinfo.ml.feature.Distance;
import com.oculusinfo.ml.feature.numeric.NumericVectorFeature;

public class EuclideanDistance extends Distance<NumericVectorFeature> {

    public EuclideanDistance(double weight) {
        super(weight);
    }

    @Override
    public double distance(NumericVectorFeature f1, NumericVectorFeature f2) {
        double[] v1 = f1.getValue();
        double[] v2 = f2.getValue();

        if (v1 == null || v2 == null) return Double.MAX_VALUE;
        if (v1.length != v2.length) return Double.MAX_VALUE;

        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            double diff = v1[i] - v2[i];
            sum += diff * diff;
        }
        return weight * Math.sqrt(sum);
    }
}
