package com.systex.billhu.demo2.Restcontroller;

import com.systex.billhu.demo2.Model.ResponseJson;
import com.systex.billhu.demo2.Service.insertMarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/Market")
public class insertMarketController {

    @Autowired
    private insertMarketService service;

    @Operation(summary = "insert CropMarket to Market table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert CropMarket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseJson.class))}),
            @ApiResponse(responseCode = "500", description = "insert CropMarket to Market table error",
                    content = @Content)
    })
    @PostMapping ("/CropMarket")
    public ResponseJson insertCropMarket(){
        int num=service.insertCropMarket();
        if(num==-1){
            return new ResponseJson( HttpStatus.INTERNAL_SERVER_ERROR,"insert CropMarket to Market table error");
        }
        return new ResponseJson( HttpStatus.OK,"insert table "+num);

    }


    @Operation(summary = "insert SeafoodMarket to Market table")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "insert SeafoodMarket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseJson.class))}),
            @ApiResponse(responseCode = "500", description = "insert SeafoodMarket to Market table error",
                    content = @Content)
    })
    @PostMapping("/SeafoodMarket")
    public ResponseJson insertSeafoodMarket(){
        int num=service.insertSeafoodMarket();
        if(num==-1){
            return new ResponseJson( HttpStatus.INTERNAL_SERVER_ERROR,"insert SeafoodMarket to Market table error");
        }
        return new ResponseJson(HttpStatus.OK,"insert table "+num );

    }


}
