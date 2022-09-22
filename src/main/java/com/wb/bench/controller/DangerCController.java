package com.wb.bench.controller;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.DangerCRequest;
import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.response.OutDangerBackResponse;
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
import java.util.Objects;

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
     * 出险信息详版
     * 下单回调
     */
    @PostMapping("/query")
    @ApiOperation("查询")
    public JSONObject query(@RequestBody @Validated DangerCRequest request){

        return dangerCService.query(request);
    }

    /**
     * 出险信息详版
     */
    @PostMapping("/queryBackData")
    @ApiOperation("出险信息详版回调")
    public OutDangerBackResponse queryBackData(@RequestBody @Validated OutDangerBackRequest request){
        log.info("出险信息详版回调=================");
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(request)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        return dangerCService.queryBackData(request);
    }

    /**
     * 出险信息详版回调
     */
    @PostMapping("/queryBackDataTest")
    @ApiOperation("出险信息详版回调")
    public OutDangerBackResponse queryBackDataTest(String data){
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(data)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        outDangerBackResponse.setCode(0);
        return outDangerBackResponse;
    }

}
