package com.systex.billhu.demo2.Service;

import com.systex.billhu.demo2.Util.insertProductAndMarketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class insertProductService {
    @Autowired
     JdbcTemplate jdbcTemplate;

    private insertProductAndMarketUtil util =new insertProductAndMarketUtil();


    private static final String INSERT_Product = "INSERT INTO Product(ProductCode,ProductName,Category) VALUES(?,?,?)";




    public int insertSeafoodProduct() {


        List<Object[]> SeafoodData =util.outputData("https://data.coa.gov.tw/api/v1/SeafoodProdType/", "SeafoodProd", "漁");
        int[] num= jdbcTemplate.batchUpdate(INSERT_Product, SeafoodData);
        if(num.length<=0){
            return -1;
        }


        return num.length;
    }

    public int insertCropProduct() {

        List<Object[]> CropData = util.outputData("https://data.coa.gov.tw/api/v1/CropType/", "Crop", "農");
        int[] num= jdbcTemplate.batchUpdate(INSERT_Product, CropData);

        if(num.length<=0){
            return -1;
        }

        return num.length;

    }


}
