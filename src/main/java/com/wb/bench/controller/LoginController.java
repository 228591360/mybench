package com.wb.bench.controller;

import com.wb.bench.base.BaseResponse;
import com.wb.bench.exception.BaseBusinessException;
import com.wb.bench.request.LoginRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(description = "login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public BaseResponse<CustomerInfoResponse> login(@RequestBody @Validated LoginRequest request)throws BaseBusinessException {
        return BaseResponse.success(loginService.login(request));
    }
}
