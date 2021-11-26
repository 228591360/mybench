package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.CustomerInfoServer;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation("客户列表分页")
    @PostMapping(value = "/queryPageList")
    public Result<BasePage<CustomerInfoResponse>> queryPageList(@RequestBody @Validated CustomerInfoRequest customerInfoRequest){
        BasePage<CustomerInfoResponse> customerInfoResponseBasePage = customerInfoServer.queryPageList(customerInfoRequest);
        return R.ok(customerInfoResponseBasePage);
    }

    /**
     * 客户列表
     * @return
     */
    @GetMapping("/queryCustomerInfo")
    public Result<List<CustomerInfoResponse>> queryCustomerInfo() {
        return R.ok(customerInfoServer.queryCustomerInfo());
    }

    /**
     * 客户详情
     * @param customerId
     * @return
     */
    @GetMapping("/queryCustomerInfoById")
    public Result<CustomerInfoResponse> queryCustomerInfoById(@Param("customerId") String customerId) {
        return R.ok(customerInfoServer.queryCustomerInfoById(customerId));
    }

    /**
     * 删除
     * @param customerId
     * @return
     */
    @GetMapping("/deleteCustomerInfoById")
    public Result deleteCustomerInfoById(@Param("customerId") String customerId) {
        customerInfoServer.deleteCustomerInfoById(customerId);
        return R.ok();
    }

    /**
     * 创建或编辑
     * @param request
     * @return
     */
    @PostMapping("/createCustomerInfo")
    public Result createCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return R.ok(customerInfoServer.createCustomerInfo(request));
    }

}
