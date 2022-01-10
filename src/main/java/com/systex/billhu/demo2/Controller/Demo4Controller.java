package com.systex.billhu.demo2.Controller;

import com.systex.billhu.demo2.Service.queryMarketService;
import com.systex.billhu.demo2.Service.queryProductService;
import com.systex.billhu.demo2.Service.queryTradedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class Demo4Controller {
    @Autowired
    queryTradedService tservice;
    @Autowired
    queryMarketService mservice;
    @Autowired
    queryProductService pservice;

    @GetMapping("/Demo4.out")
    public String processDemo4(@RequestParam("year") String year, @RequestParam("month") String month , @RequestParam("category") String category, Model model){
        List<Map<String,Object>> queryTraded=tservice.queryDemo4(year,month,category);
        List<String> allProductid=new ArrayList<>();
        if(queryTraded ==null){
            return "errorDemo4";
        }
        for(int i=0;i<queryTraded.size();i++){
            allProductid.add(String.valueOf(queryTraded.get(i).get("productid")));
            model.addAttribute("tq"+i,queryTraded.get(i).get("tq"));

        }
        List<String> allProductName= pservice.queryProductNameByProductid(allProductid);
        for (int i=0;i<allProductName.size();i++){
            model.addAttribute("productName"+i,allProductName.get(i));
        }
        model.addAttribute("category",category);
        model.addAttribute("year",year);
        model.addAttribute("month",month);

        return "Demo4output";
    }
}
