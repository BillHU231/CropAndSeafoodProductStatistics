package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Service.insertMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/Market")
public class insertMarketController {

    @Autowired
    private insertMarketService service;





    @PostMapping ("/CropMarket")
    public ResponseEntity insertCropMarket(){
        int num=service.insertCropMarket();
        if(num==-1){
            return new ResponseEntity<>("insert table error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("insert table "+num, HttpStatus.OK);

    }
    @PostMapping("/SeafoodMarket")
    public ResponseEntity insertSeafoodMarket(){
        int num=service.insertSeafoodMarket();
        if(num==-1){
            return new ResponseEntity<>("insert table error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("insert table "+num, HttpStatus.OK);

    }


}
