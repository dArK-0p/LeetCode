package org.leetcode.solutions;

import org.leetcode.core.Problem;

@SuppressWarnings("unused")
public class LC7 implements Problem<Integer, Integer> {

    @Override
    public Integer solve(Integer input) {
        long reversed = 0;
        while (input != 0) {
            reversed = reversed * 10 + input % 10;
            input /= 10;
        }
        return (reversed < Integer.MIN_VALUE || reversed > Integer.MAX_VALUE) ? 0 : (int) reversed;
    }
}