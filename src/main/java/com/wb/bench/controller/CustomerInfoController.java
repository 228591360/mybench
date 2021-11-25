package com.wb.bench.controller;

import com.wb.bench.base.BaseResponse;
import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.CustomerInfo;
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


    @ApiOperation("打卡、补打卡列表")
    @PostMapping(value = "/crmClockIn/queryPageList")
    public Result<BasePage<CustomerInfoResponse>> queryPageList(@RequestBody @Validated CustomerInfoRequest customerInfoRequest){
        BasePage<CustomerInfoResponse> crmClockInPageVOBasePage = customerInfoServer.queryPageList(customerInfoRequest);
        return R.ok();
    }

    /**
     * 客户列表
     * @return
     */
    @GetMapping("/queryCustomerInfo")
    public BaseResponse<List<CustomerInfoResponse>> queryCustomerInfo() {
        return BaseResponse.success(customerInfoServer.queryCustomerInfo());
    }

    /**
     * 客户详情
     * @param customerId
     * @return
     */
    @GetMapping("/queryCustomerInfoById")
    public BaseResponse<CustomerInfo> queryCustomerInfoById(@Param("customerId") String customerId) {
        return BaseResponse.success(customerInfoServer.queryCustomerInfoById(customerId));
    }

    /**
     * 删除
     * @param customerId
     * @return
     */
    @GetMapping("/deleteCustomerInfoById")
    public BaseResponse deleteCustomerInfoById(@Param("customerId") String customerId) {
        customerInfoServer.deleteCustomerInfoById(customerId);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 创建或编辑
     * @param request
     * @return
     */
    @PostMapping("/createCustomerInfo")
    public BaseResponse createCustomerInfo(@RequestBody @Validated CustomerInfoRequest request) {
        return BaseResponse.success(customerInfoServer.createCustomerInfo(request));
    }

}
