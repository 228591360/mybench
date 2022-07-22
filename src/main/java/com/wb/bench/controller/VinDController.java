package com.wb.bench.controller;

import com.alibaba.fastjson.JSONObject;
import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.request.VinDRequest;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.service.VinDService;
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
@Api(description = "车辆维修保养记录 D")
public class VinDController {
    @Resource
    private VinDService vinDService;



    /**
     * 车辆维修保养记录 D查询
     * @param request
     * @return
     */
    @PostMapping("/v4/query")
    @ApiOperation("车辆维修保养记录 D查询")
    public JSONObject query(@RequestBody @Validated VinDRequest request){
        log.info("车辆维修保养记录 D查询入口================={}",request.getVin());
        return vinDService.query(request);
    }


    /**
     * 车辆维修保养记录 D 回调
     */
    @PostMapping("/v4/callback")
    @ApiOperation("车辆维修保养记录 D 回调")
    public OutDangerBackResponse callback(@RequestBody @Validated OutDangerBackRequest request){
        log.info("车辆维修保养记录 D 回调=================");
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(request)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        return vinDService.callback(request);
    }



    /**
     * 车辆维修保养记录 D
     */
    @PostMapping("/v4/callbackDataMy")
    @ApiOperation("车辆维修保养记录 D 回调")
    public OutDangerBackResponse callbackDataMy(String data){
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(data)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        outDangerBackResponse.setCode(0);
        return outDangerBackResponse;
    }

}
