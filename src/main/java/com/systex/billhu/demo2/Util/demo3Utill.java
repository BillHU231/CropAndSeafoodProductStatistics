package com.systex.billhu.demo2.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class demo3Utill  {
    public boolean ifIncreasing(List queryData) {
            Double tqinit = new Double(queryData.get(0).toString());
        for (int i = 1; i < queryData.size(); i++) {
            Double tq = new Double(queryData.get(i).toString());

            if (tq > tqinit) {
                tqinit = tq;
                continue;
            } else {

                return false;
            }

        }
        return true;

    }

}
