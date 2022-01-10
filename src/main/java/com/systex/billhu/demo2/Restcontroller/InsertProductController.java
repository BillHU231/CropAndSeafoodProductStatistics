package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Model.ResponseJson;
import com.systex.billhu.demo2.Service.insertProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "insert CropProduct to Product table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert CropProduct",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseJson.class))}),
            @ApiResponse(responseCode = "500", description = "insert CropProduct to Product table error",
                    content = @Content)
    })
    @PostMapping("/CropProduct")
    public ResponseJson insertCropProduct(){
        int num=service.insertCropProduct();
        if(num==-1){
            return new ResponseJson( HttpStatus.INTERNAL_SERVER_ERROR,"insert CropProduct to Product table error");
        }
        return new ResponseJson( HttpStatus.OK,"insert table "+num);

    }

    @Operation(summary = "insert SeafoodProduct to Product table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert SeafoodProduct",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseJson.class))}),
            @ApiResponse(responseCode = "500", description = "insert SeafoodProduct to Product table error",
                    content = @Content)
    })
    @PostMapping("/SeafoodProduct")
    public ResponseJson insertSeafoodProduct(){
        int num= service.insertSeafoodProduct();
        if(num==-1){
            return new ResponseJson( HttpStatus.INTERNAL_SERVER_ERROR ,"insert table error");
        }
        return new ResponseJson( HttpStatus.OK,"insert table "+num);

    }





}
