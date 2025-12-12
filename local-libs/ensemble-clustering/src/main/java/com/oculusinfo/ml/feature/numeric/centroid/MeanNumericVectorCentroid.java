/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.numeric.centroid;

import com.oculusinfo.ml.feature.Centroid;
import com.oculusinfo.ml.feature.numeric.NumericVectorFeature;

import java.util.ArrayList;
import java.util.List;

public class MeanNumericVectorCentroid extends Centroid<NumericVectorFeature> {
    private List<double[]> values = new ArrayList<double[]>();

    public MeanNumericVectorCentroid(String name) {
        super(name);
    }

    @Override
    public void add(NumericVectorFeature feature) {
        if (feature.getValue() != null) {
            values.add(feature.getValue().clone());
        }
    }

    @Override
    public void remove(NumericVectorFeature feature) {
        if (feature.getValue() != null) {
            for (int i = 0; i < values.size(); i++) {
                double[] v = values.get(i);
                if (java.util.Arrays.equals(v, feature.getValue())) {
                    values.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public NumericVectorFeature getCentroid() {
        NumericVectorFeature centroid = new NumericVectorFeature(name);
        if (values.isEmpty()) {
            centroid.setValue(new double[0]);
            return centroid;
        }

        int dim = values.get(0).length;
        double[] mean = new double[dim];
        for (double[] v : values) {
            for (int i = 0; i < dim; i++) {
                mean[i] += v[i];
            }
        }
        for (int i = 0; i < dim; i++) {
            mean[i] /= values.size();
        }
        centroid.setValue(mean);
        return centroid;
    }

    @Override
    public void reset() {
        values.clear();
    }
}
