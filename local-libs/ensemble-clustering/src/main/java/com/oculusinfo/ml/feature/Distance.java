/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature;

public abstract class Distance<T extends Feature> {
    protected double weight;

    public Distance(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public abstract double distance(T f1, T f2);
}
