package com.systex.billhu.demo2.Controller;

import com.systex.billhu.demo2.Service.queryTradedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class Demo1Controller {
    @Autowired
    queryTradedService service;

    @GetMapping("/Demo1.out")
    public String processDemo1(@RequestParam("actionDate") String actionDate, @RequestParam("endDate") String endDate, @RequestParam("productName") String productName , @RequestParam("marketName") String marketName, Model model){
        Map<String,Object> queryMap=  service.queryDemo1(actionDate,endDate,productName,marketName);
        if(queryMap==null){
            return "errorDemo1";
        }
        String avg1= String.valueOf(queryMap.get("avg"));
        String avg2=avg1.substring(0,avg1.indexOf("."));

        model.addAttribute("avg",avg2);
        model.addAttribute("max",queryMap.get("max"));
        model.addAttribute("min",queryMap.get("min"));
        model.addAttribute("actiondate",actionDate);
        model.addAttribute("enddate",endDate);
        model.addAttribute("productName",productName);
        model.addAttribute("marketName",marketName);

        return "Demo1output";

    }

}
