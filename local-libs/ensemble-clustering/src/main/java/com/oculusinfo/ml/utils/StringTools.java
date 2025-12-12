/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.utils;

import java.util.Arrays;

public class StringTools {

    /**
     * Creates a fingerprint for clustering similar strings together.
     * Based on key collision method - removes spaces, lowercases, sorts chars.
     */
    public static String fingerPrint(String str) {
        if (str == null || str.isEmpty()) return "";

        // Convert to lowercase and remove non-alphanumeric
        String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");

        if (cleaned.isEmpty()) return "";

        // Sort characters
        char[] chars = cleaned.toCharArray();
        Arrays.sort(chars);

        // Remove duplicate characters
        StringBuilder sb = new StringBuilder();
        char prev = 0;
        for (char c : chars) {
            if (c != prev) {
                sb.append(c);
                prev = c;
            }
        }

        return sb.toString();
    }

    /**
     * Computes a simple hash for the string.
     */
    public static int hash(String str) {
        if (str == null) return 0;
        return str.hashCode();
    }
}
