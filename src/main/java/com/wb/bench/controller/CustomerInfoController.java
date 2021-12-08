package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.CustomerInfoServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Api(description = "客户管理")
public class CustomerInfoController {
    @Resource
    private CustomerInfoServer customerInfoServer;

    @ApiOperation("hello")
    @GetMapping(value = "/hello")
    public Result<BasePage<CustomerInfoResponse>> hello(){
        return R.ok("你好啊，狗子！");
    }

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
    @ApiOperation("客户列表")
    public Result<List<CustomerInfoResponse>> queryCustomerInfo(@RequestBody @Validated CustomerInfoRequest customerInfoRequest) {
        return R.ok(customerInfoServer.queryCustomerInfo());
    }

    /**
     * 客户详情
     * @param customerInfoRequest
     * @return
     */
    @PostMapping("/queryCustomerInfoById")
    @ApiOperation("客户详情")
    public Result<CustomerInfoResponse> queryCustomerInfoById(@RequestBody @Validated CustomerInfoRequest customerInfoRequest) {
        return R.ok(customerInfoServer.queryCustomerInfoById(customerInfoRequest.getCustomerId()));
    }

    /**
     * 删除
     * @param customerInfoRequest
     * @return
     */
    @PostMapping("/deleteCustomerInfoById")
    @ApiOperation("删除")
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
    @ApiOperation("创建或编辑")
    public Result editCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return R.ok(customerInfoServer.editCustomerInfo(request));
    }

}
