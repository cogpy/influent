/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature;

public abstract class Centroid<T extends Feature> {
    protected String name;

    public Centroid(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void add(T feature);
    public abstract void remove(T feature);
    public abstract T getCentroid();
    public abstract void reset();
}
