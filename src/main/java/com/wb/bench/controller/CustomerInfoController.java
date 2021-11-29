package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.CustomerInfoServer;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
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
    @PostMapping("/queryCustomerInfo")
    public Result<List<CustomerInfoResponse>> queryCustomerInfo(@RequestBody @Validated CustomerInfoRequest customerInfoRequest) {
        return R.ok(customerInfoServer.queryCustomerInfo());
    }

    /**
     * 客户详情
     * @param customerInfoRequest
     * @return
     */
    @PostMapping("/queryCustomerInfoById")
    public Result<CustomerInfoResponse> queryCustomerInfoById(@RequestBody @Validated CustomerInfoRequest customerInfoRequest) {
        return R.ok(customerInfoServer.queryCustomerInfoById(customerInfoRequest.getCustomerId()));
    }

    /**
     * 删除
     * @param customerInfoRequest
     * @return
     */
    @PostMapping("/deleteCustomerInfoById")
    public Result deleteCustomerInfoById(@RequestBody @Validated CustomerInfoRequest customerInfoRequest) {
        customerInfoServer.deleteCustomerInfoById(customerInfoRequest.getCustomerId());
        return R.ok();
    }

    /**
     * 创建或编辑
     * @param request
     * @return
     */
    @PostMapping("/editCustomerInfo")
    public Result editCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return R.ok(customerInfoServer.editCustomerInfo(request));
    }

}
