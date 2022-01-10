package com.systex.billhu.demo2.Service;

import com.systex.billhu.demo2.Util.insertProductAndMarketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class insertMarketService {
    @Autowired
     JdbcTemplate jdbcTemplate;

    private insertProductAndMarketUtil util =new insertProductAndMarketUtil();

    private static final String INSERT_Market = "INSERT INTO Market(MarketCode,MarketName,Category) VALUES(?,?,?)";

    public  int insertSeafoodMarket() {
        List<Object[]> seafoodMarket = util.outputData("https://data.coa.gov.tw/api/v1/SeafoodProdMarketType/", "SeafoodMarket", "漁");
        int[] num=jdbcTemplate.batchUpdate(INSERT_Market, seafoodMarket);

        if(num.length<=0){
            return -1;
        }


        return num.length;

    }

    public int insertCropMarket() {
        List<Object[]> cropMarket = util.outputData("https://data.coa.gov.tw/api/v1/CropMarketType/", "Market", "農");
        int[] num=jdbcTemplate.batchUpdate(INSERT_Market, cropMarket);
        if(num.length<=0){
            return -1;
        }


        return num.length;
    }

}
