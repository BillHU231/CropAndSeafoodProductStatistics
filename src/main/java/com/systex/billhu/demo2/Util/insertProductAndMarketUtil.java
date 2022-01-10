package com.systex.billhu.demo2.Util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
@Component
public class insertProductAndMarketUtil {
    public  List<Object[]> outputData(String url, String Type, String Category) {
        StringBuilder DataBuilder = new StringBuilder();
        List<Object[]> DataList=new ArrayList<>();

        URL URL = null;
        try {
            URL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try(
                InputStream ins = URL.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(ins, Charset.forName("UTF-8")));

        ) {


            String line;
            while ((line = br.readLine()) != null) {
                DataBuilder.append(line);
            }
            String Data = DataBuilder.toString();
            JSONObject jsonObject1 =new JSONObject(Data);
            JSONArray jsonArray = jsonObject1.getJSONArray("Data");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject2=jsonArray.getJSONObject(i);
                Object[] dataArray={jsonObject2.getString(Type+"Code"),jsonObject2.getString(Type+"Name"),Category};
                DataList.add(dataArray);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataList;
    }
}
