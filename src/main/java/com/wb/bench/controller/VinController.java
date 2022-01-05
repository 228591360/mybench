package com.wb.bench.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wb.bench.base.BaseResponse;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.request.OutVinRequest;
import com.wb.bench.request.VinRequest;
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
import java.util.Base64;
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
        byte[] result = Base64.getDecoder().decode(data.getBytes());
        String s = new String(result);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if(!"查询成功".equals(jsonObject.get("message").toString())){
            return "fail";
        }
        //计费逻辑
        if("0".equals(jsonObject.get("code").toString())){
            UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
            wrapper.set("toll", "是");
            wrapper.eq("order_id", jsonObject.get("orderid").toString());
            wbQueryLogService.update(wrapper);
        }
        return "success";
    }

    /**
     * 出险查询
     */
    @PostMapping("/outDange")
    @ApiOperation("出险查询")
    public String outDange(@RequestBody @Validated OutVinRequest outVinRequest){
        return vinService.outDange(outVinRequest);
    }
}
