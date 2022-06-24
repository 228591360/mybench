package com.wb.bench.controller;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.InquireRequest;
import com.wb.bench.request.PlaceAnOrderRequest;
import com.wb.bench.service.DangerCService;
import com.wb.bench.service.VinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(description = "出险信息详版")
@RequestMapping("/dangerC")
public class DangerCController {
    @Resource
    private VinService vinService;

    @Resource
    private DangerCService dangerCService;



    /**
     * 下单
     */
    @ApiOperation("下单")
    @PostMapping("/placeAnOrder")
    public JSONObject placeAnOrder(@RequestBody @Validated PlaceAnOrderRequest request){
        return dangerCService.placeAnOrder(request);
    }



    /**
     * 查询
     */
    @PostMapping("/inquire")
    @ApiOperation("查询")
    public JSONObject inquire(@RequestBody @Validated InquireRequest request){

        return dangerCService.inquire(request);
    }


}
