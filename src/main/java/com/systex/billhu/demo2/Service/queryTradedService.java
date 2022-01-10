package com.systex.billhu.demo2.Service;

import com.systex.billhu.demo2.Util.dataInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class queryTradedService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    queryProductService pService;
    @Autowired
    queryMarketService mService;
    @Autowired
    dataInterval util;

    private static final String query_Demo1 = "SELECT AVG(transquantity) ,MAX(transquantity),MIN(transquantity) FROM Traded WHERE TradedDate BETWEEN ? AND ? AND productid= ? AND marketid= ?";
    private static final String query_Demo2 = "SELECT SUM(AvgPrice) FROM Traded WHERE TradedDate=? AND Category=?;";
    private static final String query_Demo4 = "SELECT ProductId, SUM(TransQuantity) AS tq FROM Traded WHERE TradedDate BETWEEN ? AND ? AND category=?  GROUP BY  ProductId  ORDER BY tq DESC  LIMIT 10;";
    private static final String query_Demo3_1 = "SELECT TradedDate,TransQuantity FROM Traded WHERE TradedDate BETWEEN ? AND ? AND ProductId = ? AND MarketId=? ;";


    public Map<String, Object> queryDemo1(String actionDateString, String endDateString, String productName, String MarketName) {

        List<Map<String, Object>> queryDateList = new ArrayList<>();
        int productid = pService.queryProductid(productName);
        int marketid = mService.queryMarketid(MarketName);

        Date actionDate = null;
        Date endDate = null;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            actionDate = df.parse(actionDateString);
            endDate = df.parse(endDateString);


            if (productid <= 0 || marketid <= 0) {
                return null;
            }

            Map<String, Object> queryDataMap = jdbcTemplate.queryForMap(query_Demo1, new Object[]{actionDate, endDate, productid, marketid});
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

    public List<Map<String, Object>> queryDemo4(String yearString, String monthString, String category) {

        List<List<Map<String, Object>>> queryDate = new ArrayList<>();
        int year = Integer.valueOf(yearString.trim());
        int month = Integer.valueOf(monthString.trim());
        int day = util.havaDay(year, month);
        if (day == -1) {
            return null;
        }
        String startDateString = year + "-" + month + "-1";
        String endDateString = year + "-" + month + "-" + day;
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

    public Map<String,Object> queryDemo3(String dateString, String productName, String marketName) {


        Map<String, Object> queryDateMap = new TreeMap<>();


        Calendar cal = Calendar.getInstance();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(dateString);
            cal.setTime(date);
            cal.add(Calendar.DATE, -5);
            Date oldDate = cal.getTime();

            int productId = pService.queryProductid(productName);
            int marketid = mService.queryMarketid(marketName);
            List<Map<String, Object>> queryDateList3_1 = jdbcTemplate.queryForList(query_Demo3_1, new Object[]{oldDate, date, productId, marketid});

            if (queryDateList3_1.size() != 5) {
                return null;
            }

            for (Map itemMap : queryDateList3_1) {
                queryDateMap.put(String.valueOf(itemMap.get("TradedDate")), itemMap.get("TransQuantity"));
            }

          Boolean isBoolean=  ifIncreasing(queryDateMap);
            queryDateMap.put("ifIncreasing",isBoolean);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return queryDateMap;

    }




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

    public String dayToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    public String  getoldDate(String dateString){
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String olddateString="";
        Date date = null;
        try {
            date = df.parse(dateString);
        cal.setTime(date);
        cal.add(Calendar.DATE, -5);
        Date oldDate = cal.getTime();
        olddateString+=dayToString(oldDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return olddateString;
    }



}
