package com.wb.bench.controller;

import com.wb.bench.request.VipShopBatchRequest;
import com.wb.bench.service.VipShopBatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(description = "唯品维保批量")
public class VipShopBathController {
    @Resource
    private VipShopBatchService vipShopBatchService;



    /**
     * 唯品维保查询
     * @param vipShopBatchRequest
     * @return
     */
    @PostMapping("/order/weibao/batch")
    @ApiOperation("唯品维保查询")
    public String queryVinInfo(@RequestBody @Validated VipShopBatchRequest vipShopBatchRequest){
        String s = vipShopBatchService.weiBao(vipShopBatchRequest);
        return "ok";
    }



}
