package com.systex.billhu.demo2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
//swagger 文件大標題
@OpenAPIDefinition(info = @Info(title = "systex_BillHU_lab2專案Restful API 文件",version = "1.0",description = "下載農委會OpenData,來進行農和漁產品的交易行情統計"))
public class Demo2Application {

	public static void main(String[] args) {SpringApplication.run(Demo2Application.class, args);
	}

}
