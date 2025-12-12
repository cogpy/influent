/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package com.oculusinfo.ml.feature.string.distance;

import com.oculusinfo.ml.feature.Distance;
import com.oculusinfo.ml.feature.string.StringFeature;

public class EditDistance extends Distance<StringFeature> {

    public EditDistance(double weight) {
        super(weight);
    }

    @Override
    public double distance(StringFeature f1, StringFeature f2) {
        String s1 = f1.getValue();
        String s2 = f2.getValue();

        if (s1 == null && s2 == null) return 0;
        if (s1 == null || s2 == null) return 1;

        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0) return len2;
        if (len2 == 0) return len1;

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) dp[i][0] = i;
        for (int j = 0; j <= len2; j++) dp[0][j] = j;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        int maxLen = Math.max(len1, len2);
        return weight * ((double) dp[len1][len2] / maxLen);
    }
}
