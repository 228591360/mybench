package com.wb.bench.controller;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.OutDangerBatchRequest;
import com.wb.bench.request.OutVinBatchRequest;
import com.wb.bench.service.VinBatchService;
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
@Api(description = "批量维修查询")
public class VinBatchController {
    @Resource
    private VinBatchService vinBatchService;


    /**
     * 批量出险查询
     */
    @PostMapping("/outBatchDange")
    @ApiOperation("批量出险查询")
    public String outBatchDange(@RequestBody @Validated OutVinBatchRequest request){
        return vinBatchService.outDange(request);
    }


    /**
     * 批量出险查询异步
     */
    @PostMapping("/outBatchDanger")
    @ApiOperation("批量异步出险查询")
    public JSONObject outBatchDanger(@RequestBody @Validated OutDangerBatchRequest request){
        return vinBatchService.outDanger(request);
    }



}
