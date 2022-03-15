package com.wb.bench.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wb.bench.base.BaseResponse;
import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.request.*;
import com.wb.bench.response.LogResponse;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.response.StatisticsResponse;
import com.wb.bench.service.VinService;
import com.wb.bench.service.WbQueryLogService;
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

    @Resource
    private WbQueryLogService wbQueryLogService;

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
    public String queryVinInfo(@RequestBody @Validated VinRequest vinRequest){
        log.info("维保查询入口================={}",vinRequest.getVin());
        String s = vinService.queryVinInfo(vinRequest);
        return JSON.parse(s).toString();
    }

    /**
     * 维保回调
     * @param data
     * @return
     */
    @PostMapping("/freceivedata")
    @ApiOperation("维保回调")
    public String freceivedata(String data){
        log.info("维保回调=================");
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
        return "success";
    }

    /**
     * 出险查询
     */
    @PostMapping("/outDange")
    @ApiOperation("出险查询")
    public String outDange(@RequestBody @Validated OutVinRequest outVinRequest){
        log.info("出险查询入口================={}",outVinRequest.getVin());
        return vinService.outDange(outVinRequest);
    }


    /**
     * 出险查询异步
     */
    @PostMapping("/outDanger")
    @ApiOperation("异步出险查询")
    public JSONObject outDanger(@RequestBody @Validated OutDangerRequest request){
        log.info("异步出险查询入口================={}",request.getVin());
        return vinService.outDanger(request);
    }

    /**
     * 出险查询异步
     */
    @PostMapping("/outDangerBackData")
    @ApiOperation("出险查询回调")
    public OutDangerBackResponse outDangerBackData(@RequestBody @Validated OutDangerBackRequest request){
        log.info("出险查询回调=================");
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(request)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        return vinService.outDangerBackData(request);
    }



    /**
     * 出险查询异步
     */
    @PostMapping("/outDangerBackDataTest")
    @ApiOperation("出险查询回调")
    public OutDangerBackResponse outDangerBackDataTest(String data){
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        if(Objects.isNull(data)){
            outDangerBackResponse.setCode(-1);
            return outDangerBackResponse;
        }
        outDangerBackResponse.setCode(0);
        return outDangerBackResponse;
    }
    /**
     * 查询统计
     */
    @ApiOperation("查询统计分页")
    @PostMapping(value = "/statisticsPage")
    public Result<BasePage<StatisticsResponse>> queryPage(@RequestBody @Validated StatisticsRequest request){
        BasePage<StatisticsResponse> statisticsResponseBasePage = vinService.queryPage(request);
        return R.ok(statisticsResponseBasePage);
    }

    @ApiOperation("查询历史日志分页")
    @PostMapping(value = "/queryLog")
    public Result<BasePage<LogResponse>> queryLog(@RequestBody @Validated LogRequest request){
        BasePage<LogResponse> responseBasePage = vinService.queryLog(request);
        return R.ok(responseBasePage);
    }
}
