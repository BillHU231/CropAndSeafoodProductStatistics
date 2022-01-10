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
    public String processDemo2(@RequestParam("Date") String date, Model model) {
        Map<String, Object> CropTraded = service.queryDemo2(date, "農");
        Map<String, Object> SeafoodTraded = service.queryDemo2(date, "漁");
        if (CropTraded == null || SeafoodTraded == null) {
            return "errorDemo2";
        }
        //Cropsum
        Double CropsumDouble = Double.valueOf(CropTraded.get("sum").toString());
        Long CropLong = CropsumDouble.longValue();
        //seafoodsum
        Double SeafoodDouble = Double.valueOf(SeafoodTraded.get("sum").toString());
        Long SeafoodLong = SeafoodDouble.longValue();

        if (CropLong == -1 && SeafoodLong != -1) {
            model.addAttribute("date", date);
            model.addAttribute("crop", "無交易");
            model.addAttribute("seafood", SeafoodLong);
        } else if (CropLong != -1 && SeafoodLong == -1) {
            model.addAttribute("date", date);
            model.addAttribute("crop", CropLong);
            model.addAttribute("seafood", "無交易");
        } else if (CropLong == -1 && SeafoodLong == -1) {
            model.addAttribute("date", date);
            model.addAttribute("crop", "無交易");
            model.addAttribute("seafood", "無交易");
        } else {
            model.addAttribute("date", date);
            model.addAttribute("crop", CropLong);
            model.addAttribute("seafood", SeafoodLong);

        }

        return "Demo2output";

    }

}
