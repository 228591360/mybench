package com.wb.bench.controller;

import com.alibaba.fastjson.JSON;
import com.wb.bench.base.BaseResponse;
import com.wb.bench.request.VinRequest;
import com.wb.bench.service.VinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class VinController {
    @Resource
    private VinService vinService;

    @PostMapping("/queryInfo")
    public BaseResponse queryInfo(@RequestBody @Validated VinRequest vinRequest) throws Exception {
        String s = vinService.queryInfo(vinRequest);
        return BaseResponse.success(JSON.parse(s));
    }

    @PostMapping("/v1/queryInfo")
    public BaseResponse queryVinInfo(@RequestBody @Validated VinRequest vinRequest) throws Exception {
        String s = vinService.queryVinInfo(vinRequest);
        return BaseResponse.success(JSON.parse(s));
    }
}
