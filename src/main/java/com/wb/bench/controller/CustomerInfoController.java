package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
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
    public Result<List<CustomerInfo>> queryCustomerInfo() {
        return R.ok(customerInfoServer.queryCustomerInfo());
    }

    @GetMapping("/queryCustomerInfoById")
    public Result<CustomerInfo> queryCustomerInfoById(@Param("customerId") String customerId) {
        return R.ok(customerInfoServer.queryCustomerInfoById(customerId));
    }

    @GetMapping("/deleteCustomerInfoById")
    public Result deleteCustomerInfoById(@Param("customerId") String customerId) {
        customerInfoServer.deleteCustomerInfoById(customerId);
        return R.ok();
    }

    @PostMapping("/createCustomerInfo")
    public Result createCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return R.ok(customerInfoServer.createCustomerInfo(request));
    }

}
