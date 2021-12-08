package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.request.CustomerServiceRequest;
import com.wb.bench.response.CustomerServiceResponse;
import com.wb.bench.service.CustomerService;
import io.swagger.annotations.Api;
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
@Api(description = "客户服务")
public class CustomerServiceController {
    @Resource
    private CustomerService customerService;


    /**
     * 客户列表
     * @return
     */
    @PostMapping("/queryList")
    @ApiOperation("客户服务列表")
    public Result<List<CustomerServiceResponse>> queryList(@RequestBody @Validated CustomerServiceRequest customerServiceRequest) {
        return R.ok(customerService.queryList(customerServiceRequest));
    }


    /**
     * 删除
     * @param customerServiceRequest
     * @return
     */
    @PostMapping("/deleteByCustomerIdAndServiceId")
    @ApiOperation("删除")
    public Result deleteByCustomerIdAndServiceId(@RequestBody @Validated CustomerServiceRequest customerServiceRequest) {
        customerService.deleteByCustomerIdAndServiceId(customerServiceRequest);
        return R.ok();
    }

    /**
     * 编辑
     * @param customerServiceRequest
     * @return
     */
    @PostMapping("/editCustomerService")
    @ApiOperation("编辑")
    public Result editCustomerService(@RequestBody @Validated CustomerServiceRequest customerServiceRequest) {
        return R.ok(customerService.editCustomerService(customerServiceRequest));
    }

}
