package com.systex.billhu.demo2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseJson {
    @NotBlank
    private HttpStatus code;
    @NotBlank
    private String Message;
}
