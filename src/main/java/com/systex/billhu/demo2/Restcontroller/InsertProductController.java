package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Service.insertProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("api/Product")
public class InsertProductController {

    @Autowired
    private insertProductService service;






    @PostMapping("/CropProduct")
    public ResponseEntity insertCropProduct(){
        int num=service.insertCropProduct();
        if(num==-1){
            return new ResponseEntity<>("insert table error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("insert table "+num, HttpStatus.OK);

    }
    @PostMapping("/SeafoodProduct")
    public ResponseEntity insertSeafoodProduct(){
        int num= service.insertSeafoodProduct();
        if(num==-1){
            return new ResponseEntity<>("insert table error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("insert table "+num, HttpStatus.OK);

    }





}
