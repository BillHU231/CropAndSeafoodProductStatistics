package com.systex.billhu.demo2.Controller;

import com.systex.billhu.demo2.Service.queryTradedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Controller
public class Demo3Controller {
    @Autowired
    queryTradedService service;

    @GetMapping("/Demo3.out")
    public String processDem03(@RequestParam("date") String dateString, @RequestParam("productName") String productName, @RequestParam("marketName") String marketName, Model model){
       Map<String,Object> queryData=  service.queryDemo3(dateString,productName,marketName);
       if(queryData ==null){
           return "errorDemo3";
       }
       model.addAttribute("ifIncreasing",queryData.get("ifIncreasing"));
       model.addAttribute("productName",productName);
       model.addAttribute("marketName",marketName);
       model.addAttribute("newdate",dateString);
       model.addAttribute("olddate",service.getoldDate(dateString));

       return "Demo3output";

    }


}
