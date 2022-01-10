package com.systex.billhu.demo2.Service;

import com.systex.billhu.demo2.Util.dataInterval;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class insertTradedService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    queryMarketService queryMarket;
    @Autowired
    queryProductService queryParoduct;
    @Autowired
    dataInterval dataInterval;

    private static final String INTERT_Trading = "INSERT INTO Traded(TradedDate,ProductId,MarketId,Category,AvgPrice,TransQuantity) VALUES(?,?,?,?,?,?)";


    public int inputData() throws ParseException {
        int insertTotal = 0;
        List<String> marketCodeList = queryMarket.getMarketCode();
        List<String> dateInterval = dataInterval.getDataInterval();

        URL URL = null;

        for (String date : dateInterval) {
            List<Object[]> tradingData = new ArrayList<>();
            for (String marketCode : marketCodeList) {
                log.info("insert date {} marketCode {}", date, marketCode);
                try {

                    String url = "https://data.coa.gov.tw/api/v1/AgriFisheryProductsTransTypeType/?TransDate=" + date + "&MarketCode=" + marketCode;
                    URL = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try (

                        InputStream ins = URL.openStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(ins, Charset.forName("UTF-8")));
                ) {
                    StringBuilder Sourcedata = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        Sourcedata.append(line);
                    }
                    String Data = Sourcedata.toString();
                    JSONObject jsonObject1 = new JSONObject(Data);
                    JSONArray jsonArray = jsonObject1.getJSONArray("Data");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object[] dataArray = new Object[6];
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);


                        if ("休市".equals(jsonObject2.getString("ProdName"))) {
                            continue;
                        } else {

                            //TransDate
                            StringBuilder tradingDate = new StringBuilder();
                            String dateData = jsonObject2.getString("TransDate").replace(" ", "");
                            int year = Integer.valueOf(dateData.substring(0, 3)) + 1911;
                            int month = Integer.valueOf(dateData.substring(3, 5));
                            int day = Integer.valueOf(dateData.substring(5));
                            tradingDate.append(year + "-" + month + "-" + day);
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date tranDate = df.parse(tradingDate.toString());
                            LocalDate localDate = dataInterval.convertToLocalDateViaInstant(tranDate);







                            int productid = queryParoduct.queryProductidByproductNameAndproductCode(jsonObject2.getString("ProdName").trim(), jsonObject2.getString("ProdCode").trim());
                            int Marketid = queryMarket.QUERYMarketidByMarketNameAndMarketCode(jsonObject2.getString("MarketName").trim(), jsonObject2.getString("MarketCode").trim());


                            if (productid > 0 && Marketid > 0) {
                                dataArray[0] = localDate;
                                dataArray[1] = productid;
                                dataArray[2] = Marketid;
                                dataArray[3] = jsonObject2.getString("Category");
                                dataArray[4] = jsonObject2.getDouble("Avg_Price");
                                dataArray[5] = jsonObject2.getDouble("Trans_Quantity");

                                tradingData.add(dataArray);
                            } else {
                                continue;
                            }

                        }

                    }


                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
            if (tradingData.size() > 0) {
                int num = insertTrading(tradingData);
                if (num != -1) {
                    insertTotal += num;
                }
            }
        }
        return insertTotal;

    }


    private int insertTrading(List<Object[]> CropTrading) throws ParseException {

        if (CropTrading != null) {
            int[] num = jdbcTemplate.batchUpdate(INTERT_Trading, CropTrading);
            log.info("insert database {}", num.length);
            return num.length;
        } else {
            log.error("insert trading error");
            return -1;

        }

    }
}
