package com.systex.billhu.demo2.Util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import java.util.*;

@Component
public class demo3Utill  {
    public boolean ifIncreasing(List queryData) {
        Queue<Double> tqQueue = new ArrayDeque<>();
        int i = 0;
        for (Object item : queryData) {
            Double tq = new Double(item.toString().trim());

            if (i == 0) {
                i++;
                tqQueue.add(tq);
                continue;
            }
            if(tq>tqQueue.poll()){
                i++;
                tqQueue.add(tq);
                continue;
            }else{
                break;
            }

        }

        if(i==queryData.size()&&i>0){
            return true;
        }

        return false;


    }


}
