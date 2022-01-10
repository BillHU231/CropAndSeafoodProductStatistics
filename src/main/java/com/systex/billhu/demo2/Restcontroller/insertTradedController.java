package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Service.insertTradedService;
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

import java.text.ParseException;
@RestController
@RequestMapping("api/Traded")
public class insertTradedController {
    @Autowired
    insertTradedService  service;





    @PostMapping("/allTraded")
    public ResponseEntity processInsertTraded() throws ParseException {
        int num = service.inputData();
        if(num==-1){
            return new ResponseEntity<>("insert table error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("insert table "+num, HttpStatus.OK);


    }
}
