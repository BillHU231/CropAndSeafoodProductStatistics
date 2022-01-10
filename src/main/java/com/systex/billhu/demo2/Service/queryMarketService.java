package com.systex.billhu.demo2.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class queryMarketService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String QUERY_MarketCode = "SELECT MarketCode FROM Market ";
    private static final String QUERY_MarketidByMarketNameAndMarketCode = "SELECT id FROM Market WHERE MarketName = ? AND MarketCode= ?";
    private static final String QUERY_MarketidByMarketName = "SELECT id FROM Market WHERE MarketName = ? ";




    public List<String> getMarketCode() {
        List<Map<String, Object>> queryForMaps = jdbcTemplate.queryForList(QUERY_MarketCode);
        List<String> MarketCode = new ArrayList<>();

        for (Map itemMap : queryForMaps) {
            for (Object itemObject : itemMap.values()) {
                MarketCode.add((String) itemObject);
            }
        }

        return MarketCode;
    }

    public int QUERYMarketidByMarketNameAndMarketCode(String MarketName, String marketCode) {

        try {
            Map<String, Object> queryData = jdbcTemplate.queryForMap(QUERY_MarketidByMarketNameAndMarketCode, new Object[]{MarketName, marketCode});
            if (queryData.size() == 1) {
                for (Object item : queryData.values()) {

                    return (Integer) item;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("error from no MarhetNameid {}", MarketName);
            return -2;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("error from two MarhetNameid {}", MarketName);
            return -2;

        }
        return -1;


    }
    public int queryMarketid(String MarketName) {

        try {
            Map<String, Object> queryData = jdbcTemplate.queryForMap(QUERY_MarketidByMarketName, new Object[]{MarketName});
            if (queryData.size() == 1) {
                for (Object item : queryData.values()) {

                    return (Integer) item;
                }
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("error from no MarhetNameid {}", MarketName);
            return -2;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("error from two MarhetNameid {}", MarketName);
            return -2;

        }
        return -1;


    }
}
