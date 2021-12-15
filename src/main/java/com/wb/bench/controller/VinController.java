package com.wb.bench.controller;

import com.alibaba.fastjson.JSON;
import com.wb.bench.base.BaseResponse;
import com.wb.bench.request.VinRequest;
import com.wb.bench.service.VinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RestController
@Api(description = "维修查询")
public class VinController {
    @Resource
    private VinService vinService;

    @PostMapping("/queryInfo")
    @ApiOperation("")
    public BaseResponse queryInfo(@RequestBody @Validated VinRequest vinRequest) throws Exception {
        String s = vinService.queryInfo(vinRequest);
        return BaseResponse.success(JSON.parse(s));
    }

    /**
     * 维保查询
     * @param vinRequest
     * @return
     */
    @PostMapping("/v1/queryInfo")
    @ApiOperation("维保查询")
    public BaseResponse queryVinInfo(@RequestBody @Validated VinRequest vinRequest){
        String s = vinService.queryVinInfo(vinRequest);
        return BaseResponse.success(JSON.parse(s));
    }

    /**
     * 维保回调
     * @param data
     * @return
     */
    @PostMapping("/freceivedata")
    @ApiOperation("维保回调")
    public String freceivedata(String data){
        if(Objects.isNull(data)){
            return "fail";
        }
        return vinService.freceivedata(data);
    }

    @PostMapping("/backdata")
    @ApiOperation("")
    public String backdata(String data){
        if(Objects.isNull(data)){
            return "fail";
        }
        System.out.println(data);
        return "success";
    }

    /**
     * 出险查询
     */
    @PostMapping("/outDange")
    @ApiOperation("出险查询")
    public BaseResponse outDange(@RequestBody @Validated VinRequest vinRequest){
        String s = vinService.outDange(vinRequest);
        return BaseResponse.success(JSON.parse(s));
    }
}
