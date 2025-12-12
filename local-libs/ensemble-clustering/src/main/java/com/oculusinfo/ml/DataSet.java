/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSet implements Iterable<Instance> {
    private List<Instance> instances = new ArrayList<Instance>();

    public void add(Instance instance) {
        instances.add(instance);
    }

    public Instance get(int index) {
        return instances.get(index);
    }

    public int size() {
        return instances.size();
    }

    public List<Instance> getInstances() {
        return instances;
    }

    @Override
    public Iterator<Instance> iterator() {
        return instances.iterator();
    }
}
