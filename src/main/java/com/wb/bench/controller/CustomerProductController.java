package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.request.CustomerProductRequest;
import com.wb.bench.response.CustomerProductResponse;
import com.wb.bench.service.CustomerProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/customerProduct")
public class CustomerProductController {
    @Resource
    private CustomerProductService customerProductService;


    /**
     * 客户产品列表
     * @return
     */
    @PostMapping("/queryList")
    @ApiOperation("客户产品列表")
    public Result<List<CustomerProductResponse>> queryList(@RequestBody @Validated CustomerProductRequest customerProductRequest) {
        return R.ok(customerProductService.queryList(customerProductRequest));
    }


    /**
     * 删除
     * @param customerProductRequest
     * @return
     */
    @PostMapping("/deleteByCustomerIdAndProductId")
    @ApiOperation("删除")
    public Result deleteByCustomerIdAndProductId(@RequestBody @Validated CustomerProductRequest customerProductRequest) {
        customerProductService.deleteByCustomerIdAndProductId(customerProductRequest);
        return R.ok();
    }

    /**
     * 编辑
     * @param customerProductRequest
     * @return
     */
    @PostMapping("/editCustomerService")
    @ApiOperation("编辑")
    public Result editCustomerService(@RequestBody @Validated CustomerProductRequest customerProductRequest) {
        return R.ok(customerProductService.editCustomerProduct(customerProductRequest));
    }

}
