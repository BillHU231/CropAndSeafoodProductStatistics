package com.systex.billhu.demo2.Controller;

import com.systex.billhu.demo2.Service.queryTradedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Slf4j
@Controller
public class Demo2Controller {
    @Autowired
    queryTradedService service;

    @GetMapping("/Demo2.out")
    public String processDemo2(@RequestParam("Date") String date, Model model){
        Map<String,Object> CropTraded=  service.queryDemo2(date,"農");
        Map<String,Object> SeafoodTraded=  service.queryDemo2(date,"漁");
        log.info("test");
        if(CropTraded==null||SeafoodTraded==null){
            return "errorDemo2";
        }
        //Cropsum
        String Cropsum1= String.valueOf(CropTraded.get("sum"));
        String Cropsum2=Cropsum1.substring(0,Cropsum1.indexOf("."));
        //seafoodsum
        String Seafoodsum1= String.valueOf(SeafoodTraded.get("sum"));
        String Seafoodsum2=Seafoodsum1.substring(0,Seafoodsum1.indexOf("."));

        model.addAttribute("date",date);
        model.addAttribute("crop",Cropsum2);
        model.addAttribute("seafood",Seafoodsum2);

        return "Demo2output";

    }

}
