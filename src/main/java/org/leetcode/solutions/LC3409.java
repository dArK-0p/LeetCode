package org.leetcode.solutions;
@SuppressWarnings("unused")
public class LC3409 {
}

//protected int longestSubsequence(int[] nums) {
//    Map<Integer, Integer> dp = new HashMap<>();
//    int maxLen = 0;
//
//    for (int num : nums) {
//        int len = dp.getOrDefault(num + 1, 0) + 1;
//        dp.put(num, Math.max(dp.getOrDefault(num, 0), len));
//        maxLen = Math.max(maxLen, dp.get(num));
//    }
//
//    return maxLen;
//}