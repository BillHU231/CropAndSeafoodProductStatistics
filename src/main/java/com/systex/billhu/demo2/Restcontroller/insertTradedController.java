package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Model.ResponseJson;
import com.systex.billhu.demo2.Service.insertTradedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "insert CropAndSeafoodTraded to Traded table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert Traded",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseJson.class))}),
            @ApiResponse(responseCode = "500", description = "insert CropAndSeafoodTraded to Traded table error",
                    content = @Content)
    })
    @PostMapping("/allTraded")
    public ResponseJson processInsertTraded() throws ParseException {
        int num = service.inputData();
        if(num==-1){
            return new ResponseJson( HttpStatus.INTERNAL_SERVER_ERROR,"insert table error");
        }
        return new ResponseJson( HttpStatus.OK,"insert table "+num);


    }
}
