/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.string;

import com.oculusinfo.ml.feature.Feature;

public class StringFeature extends Feature {
    private String value;

    public StringFeature(String name) {
        super(name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
