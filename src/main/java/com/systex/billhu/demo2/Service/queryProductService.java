package com.systex.billhu.demo2.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class queryProductService   {
    @Autowired
    JdbcTemplate jdbcTemplate;


    private static final String QUERY_ProductidByProductNameAndProductCode = "SELECT id FROM Product WHERE ProductName = ? AND ProductCode = ?";
    private static final String QUERY_ProductidByProductName = "SELECT id FROM Product WHERE ProductName = ? ";
    private static final String QUERY_ProductNameByProductid = "SELECT ProductName FROM Product WHERE id = ? ";


    public int queryProductidByproductNameAndproductCode(String productName, String productCode) {
        try {
            Map<String, Object> queryData = jdbcTemplate.queryForMap(QUERY_ProductidByProductNameAndProductCode, new Object[]{productName, productCode});

            if (queryData.size() == 1) {
                for (Object item : queryData.values()) {
                    return (Integer) item;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("error from no productid {}", productName);
            return -2;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("error from two productid {}", productName);
            return -2;

        }

        return -1;

    }

    public int queryProductid(String productName) {
        try {
            Map<String, Object> queryData = jdbcTemplate.queryForMap(QUERY_ProductidByProductName, new Object[]{productName});

            if (queryData.size() == 1) {
                for (Object item : queryData.values()) {
                    return (Integer) item;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("error from no productid {}", productName);
            return -2;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("error from two productid {}", productName);
            return -2;

        }

        return -1;

    }

    public List<String> queryProductNameByProductid(List<String> productidArray) {
        List<String> allProductName=new ArrayList<>();
        for (String item : productidArray) {
            Map<String,Object> queryMap= jdbcTemplate.queryForMap(QUERY_ProductNameByProductid ,new Object[]{Integer.valueOf(item)});
            allProductName.add(String.valueOf(queryMap.get("ProductName")));
        }

        return allProductName;

    }


}
