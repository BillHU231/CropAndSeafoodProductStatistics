package com.systex.billhu.demo2.Util;

import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class demo3Utill {
    public boolean ifIncreasing(Map queryData) {

        Set<Double> tqSet = new TreeSet<>();
        List<Double> tqList = new ArrayList<>();
        for (Object item : queryData.values()) {
            Double tq = new Double(item.toString().trim());
            tqSet.add(tq);
            tqList.add(tq);
        }
        if (tqSet.size() != 5) {
            return false;
        }
        int num = 0;
        int i=0;
        for (Double itemset : tqSet) {
            if (itemset == tqList.get(i)) {
                num++;
            }
            i++;
        }
        if (num == 5) {

            return true;
        }
        return false;


    }
}
