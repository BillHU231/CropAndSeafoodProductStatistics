package com.systex.billhu.demo2.Util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class dataInterval {
    public List<String> getDataInterval() {

        List<String> dateList = new ArrayList<>();
        for (int i = 2020; i <= LocalDate.now().getYear(); i++) {
            int chinaYear = i - 1911;
            for (int j = 1; j <= 12; j++) {
                int daysInMonth = YearMonth.of(i, j).lengthOfMonth();
                String datetext;

                if (i == LocalDate.now().getYear()) {
                    if (j > LocalDate.now().getMonthValue()) {
                        break;
                    } else {
                        int day = Integer.parseInt(LocalDate.now().toString().split("-")[2]);

                        for (int k = 1; k <= day; k++) {

                            if (j < 10 && k >= 10) {
                                datetext = chinaYear + ".0" + j + "." + k;
                            } else if (j >= 10 && k < 10) {
                                datetext = chinaYear + "." + j + ".0" + k;
                            } else if (j < 10 && k < 10) {
                                datetext = chinaYear + ".0" + j + ".0" + k;
                            } else {
                                datetext = chinaYear + "." + j + "." + k;
                            }
                            dateList.add(datetext);

                        }
                        break;
                    }
                }

                for (int k = 1; k <= daysInMonth; k++) {

                    if (j < 10 && k >= 10) {
                        datetext = chinaYear + ".0" + j + "." + k;
                    } else if (j >= 10 && k < 10) {
                        datetext = chinaYear + "." + j + ".0" + k;
                    } else if (j < 10 && k < 10) {
                        datetext = chinaYear + ".0" + j + ".0" + k;
                    } else {
                        datetext = chinaYear + "." + j + "." + k;
                    }
                    dateList.add(datetext);

                }
            }
        }

        return dateList;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public int havaDay(int year,int month){
        int day=0;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day+=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day+=30;
                break;
            case 2:
                if(year %4==0 && year%100 !=0 || year % 400== 0){
                        day+=29;
                        break;
                }else{
                    day+=28;
                    break;
                }
            default:
                day--;
        }
        return day;
    }

}
