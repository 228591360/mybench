package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.ProductRequest;
import com.wb.bench.response.ProductResponse;
import com.wb.bench.service.ProductServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: WangBiao
 * @Date: 2020/12/9 16:03
 */
@Slf4j
@RestController
@Api(description = "产品管理")
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductServer productServer;

    /**
     * 产品列表分页
     * @param productRequest
     * @return
     */

    @ApiOperation("产品列表分页")
    @PostMapping(value = "/queryPageList")
    public Result<BasePage<ProductResponse>> queryPageList(@RequestBody @Validated ProductRequest productRequest){
        BasePage<ProductResponse> productResponseBasePage = productServer.queryPageList(productRequest);
        return R.ok(productResponseBasePage);
    }



    /**
     * 删除
     * @param productRequest
     * @return
     */
    @PostMapping("/deleteProductById")
    @ApiOperation("删除")
    public Result deleteProductById(@RequestBody @Validated ProductRequest productRequest) {
        productServer.deleteById(productRequest.getId());
        return R.ok();
    }

    /**
     * 创建或编辑
     * @param productRequest
     * @return
     */
    @PostMapping("/editProduct")
    @ApiOperation("创建或编辑")
    public Result editProduct(@RequestBody @Validated ProductRequest productRequest) {
        return R.ok(productServer.edit(productRequest));
    }

}
