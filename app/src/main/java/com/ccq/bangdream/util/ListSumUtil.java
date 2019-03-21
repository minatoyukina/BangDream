package com.ccq.bangdream.util;

import java.util.List;

public class ListSumUtil {
    public static int sum(List<Integer> list) {
        int sum = 0;
        for (Integer aList : list) {
            sum += aList;
        }
        return sum;
    }
}
