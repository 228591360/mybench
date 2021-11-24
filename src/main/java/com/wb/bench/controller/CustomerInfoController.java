package com.wb.bench.controller;

import com.wb.bench.base.BaseResponse;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.service.CustomerInfoServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/9 16:03
 */
@Slf4j
@RestController
public class CustomerInfoController {
    @Resource
    private CustomerInfoServer customerInfoServer;

    @GetMapping("/queryCustomerInfo")
    public BaseResponse<List<CustomerInfo>> queryCustomerInfo() {
        return BaseResponse.success(customerInfoServer.queryCustomerInfo());
    }

    @GetMapping("/queryCustomerInfoById")
    public BaseResponse<CustomerInfo> queryCustomerInfoById(@Param("customerId") String customerId) {
        return BaseResponse.success(customerInfoServer.queryCustomerInfoById(customerId));
    }

    @GetMapping("/deleteCustomerInfoById")
    public BaseResponse deleteCustomerInfoById(@Param("customerId") String customerId) {
        customerInfoServer.deleteCustomerInfoById(customerId);
        return BaseResponse.SUCCESSFUL();
    }

    @PostMapping("/createCustomerInfo")
    public BaseResponse createCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return BaseResponse.success(customerInfoServer.createCustomerInfo(request));
    }

}
