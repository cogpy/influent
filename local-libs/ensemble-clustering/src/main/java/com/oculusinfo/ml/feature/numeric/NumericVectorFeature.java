/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.numeric;

import com.oculusinfo.ml.feature.Feature;

public class NumericVectorFeature extends Feature {
    private double[] value;

    public NumericVectorFeature(String name) {
        super(name);
    }

    public double[] getValue() {
        return value;
    }

    public void setValue(double[] value) {
        this.value = value;
    }
}
