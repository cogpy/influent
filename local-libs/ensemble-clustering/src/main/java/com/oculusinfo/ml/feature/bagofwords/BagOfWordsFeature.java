/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.bagofwords;

import com.oculusinfo.ml.feature.Feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BagOfWordsFeature extends Feature {
    private Map<String, Integer> wordCounts = new HashMap<String, Integer>();

    public BagOfWordsFeature(String name) {
        super(name);
    }

    public void setCount(String word, int count) {
        wordCounts.put(word, count);
    }

    public int getCount(String word) {
        Integer count = wordCounts.get(word);
        return count != null ? count : 0;
    }

    public Set<String> getWords() {
        return wordCounts.keySet();
    }

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }
}
