package com.systex.billhu.demo2.Service;

import com.systex.billhu.demo2.Util.dateUtil;
import com.systex.billhu.demo2.Util.demo3Utill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class queryTradedService  {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    queryProductService pService;
    @Autowired
    queryMarketService mService;
    @Autowired
    dateUtil util;
    @Autowired
    demo3Utill demo3utill;

    private static final String query_Demo1 = "SELECT AVG(transquantity) ,MAX(transquantity),MIN(transquantity) FROM Traded WHERE TradedDate BETWEEN ? AND ? AND productid= ? AND marketid= ?";
    private static final String query_Demo2 = "SELECT SUM(AvgPrice) FROM Traded WHERE TradedDate=? AND Category=?;";
    private static final String query_Demo4 = "SELECT ProductId, SUM(TransQuantity) AS tq FROM Traded WHERE TradedDate BETWEEN ? AND ? AND category=?  GROUP BY  ProductId  ORDER BY tq DESC  LIMIT 10;";
    private static final String query_Demo3_1 = "SELECT TradedDate,TransQuantity FROM Traded WHERE TradedDate BETWEEN ? AND ? AND ProductId = ? AND MarketId=? ORDER BY TradedDate;";


    public Map<String, Object> queryDemo1(String actionDateString, String endDateString, String productName, String MarketName) {

        List<Map<String, Object>> queryDateList = new ArrayList<>();
        int productid = pService.queryProductid(productName);
        int marketid = mService.queryMarketid(MarketName);

        Date actionDate = null;
        Date endDate1 = null;
        Date endDate2 = null;
        Calendar cal=Calendar.getInstance();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            actionDate = df.parse(actionDateString);
            endDate1 = df.parse(endDateString);
            cal.setTime(endDate1);
            cal.add(Calendar.DATE,+1);
            endDate2=cal.getTime();


            if (productid <= 0 || marketid <= 0) {
                return null;
            }

            Map<String, Object> queryDataMap = jdbcTemplate.queryForMap(query_Demo1, new Object[]{actionDate, endDate2, productid, marketid});
            for (Object item : queryDataMap.values()) {
                if (item == null || queryDataMap.values().size() != 3) {
                    return null;
                }
            }
            queryDateList.add(queryDataMap);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (queryDateList.size() != 1) {
            return null;
        }

        return queryDateList.get(0);


    }

    public Map<String, Object> queryDemo2(String date, String Category) {
        List<Map<String, Object>> queryDateList = new ArrayList<>();

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date actionDate = df.parse(date);
            Map<String, Object> queryDataMap = jdbcTemplate.queryForMap(query_Demo2, new Object[]{actionDate, Category});
            for (Object item : queryDataMap.values()) {
                if (item == null || queryDataMap.values().size() != 1) {
                    return Map.of("sum", "-1");
                }
            }
            queryDateList.add(queryDataMap);
            log.info("test");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (queryDateList.size() != 1) {
            return null;
        }

        return queryDateList.get(0);

    }

    public boolean queryDemo3(String dateString, String productName, String marketName) {
        Boolean isBoolean=false;
        List<String> queryDateList = new ArrayList<>();


        Calendar cal = Calendar.getInstance();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(dateString);
            cal.setTime(date);
            Date nowDate=cal.getTime();
            cal.add(Calendar.DATE, -5);
            Date oldDate = cal.getTime();

            int productId = pService.queryProductid(productName);
            int marketid = mService.queryMarketid(marketName);
            List<Map<String, Object>> queryDateList3_1 = jdbcTemplate.queryForList(query_Demo3_1, new Object[]{oldDate, nowDate, productId, marketid});

            if (queryDateList3_1.size() != 5) {
                return false;
            }
            for(Map<String,Object> item:queryDateList3_1){
                queryDateList.add(item.get("TransQuantity").toString());
            }

          isBoolean=demo3utill.ifIncreasing(queryDateList);

            queryDateList.add(String.valueOf(isBoolean));


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return isBoolean;

    }



    public List<Map<String, Object>> queryDemo4(String yearString, String monthString, String category) {

        List<List<Map<String, Object>>> queryDate = new ArrayList<>();
        int year = Integer.valueOf(yearString.trim());
        int month = Integer.valueOf(monthString.trim());

        String startDateString = year + "-" + month + "-1";
        String endDateString = year + "-" + (month+1) + "-1" ;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = df.parse(startDateString);
            Date endDate = df.parse(endDateString);

            List<Map<String, Object>> allqueryDate = jdbcTemplate.queryForList(query_Demo4, new Object[]{startDate, endDate, category});

            if (allqueryDate.size() != 10) {
                return null;
            }

            queryDate.add(allqueryDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return queryDate.get(0);

    }




}
