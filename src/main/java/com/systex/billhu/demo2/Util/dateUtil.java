package com.systex.billhu.demo2.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class dateUtil {
    public List<String> getDataInterval( ) {
        List<String> dateList = new ArrayList<>();
        String startDateString="2020-01-01";
        Date nowDate = LocalDateToUtilDate(LocalDateTime.now());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date startDate = null;
        try {
            startDate = df.parse(startDateString);
            int hasday = (int) ((nowDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000));
            cal.setTime(startDate);
            for (int i = 1; i <= hasday; i++) {
                if (i == 1) {
                    int year = cal.get(Calendar.YEAR) - 1911;
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DATE);
                    String date1 = null;
                    if (month < 10 && day >= 10) {
                        date1 =  year + ".0" + month + "." + day ;
                    } else if (month >= 10 && day < 10) {
                        date1 = year + "." + month + ".0" + day ;

                    } else if (month < 10 && day < 10) {
                        date1 =  year + ".0" + month + ".0" + day ;

                    } else {
                        date1 = year + "." + month + "." + day ;

                    }
                    dateList.add(date1);
                }
                cal.add(Calendar.DATE, +1);
                int year = cal.get(Calendar.YEAR) - 1911;
                int month = cal.get(Calendar.MONTH)+1;
                int day = cal.get(Calendar.DATE);
                String date2 = null;
                if (month < 10 && day >= 10) {
                    date2 =  year + ".0" + month + "." + day;
                } else if (month >= 10 && day < 10) {
                    date2 = year + "." + month + ".0" + day ;

                } else if (month < 10 && day < 10) {
                    date2 =  year + ".0" + month + ".0" + day ;

                } else {
                    date2 =  year + "." + month + "." + day ;

                }

                dateList.add(date2);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateList;
    }

    private Date LocalDateToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String dayToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public String getOldDate(String dateString) {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String olddateString = "";
        Date date = null;
        try {
            date = df.parse(dateString);
            cal.setTime(date);
            cal.add(Calendar.DATE, -5);
            Date oldDate = cal.getTime();
            olddateString += dayToString(oldDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return olddateString;
    }


}
