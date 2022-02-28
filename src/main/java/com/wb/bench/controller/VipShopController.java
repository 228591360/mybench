package com.wb.bench.controller;

import com.alibaba.fastjson.JSON;
import com.wb.bench.request.VipShopCallbackRequest;
import com.wb.bench.request.VipShopRequest;
import com.wb.bench.response.VipShopResponse;
import com.wb.bench.service.VipShopService;
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
@Api(description = "唯品维保")
public class VipShopController {
    @Resource
    private VipShopService vipShopService;



    /**
     * 唯品维保查询
     * @param vipShopRequest
     * @return
     */
    @PostMapping("/order/weibao")
    @ApiOperation("唯品维保查询")
    public String queryVinInfo(@RequestBody @Validated VipShopRequest vipShopRequest){
        log.info("唯品维保查询入口=================");
        String s = vipShopService.weiBao(vipShopRequest);
        return JSON.parse(s).toString();
    }

    /**
     * 唯品维保回调
     * @param vipShopCallbackRequest
     * @return
     */
    @PostMapping("/weibao/callback")
    @ApiOperation("唯品维保回调")
    public VipShopResponse callbackData(@RequestBody @Validated VipShopCallbackRequest vipShopCallbackRequest){
        log.info("唯品维保回调=================");
        VipShopResponse vipShopResponse = new VipShopResponse();
        if(Objects.isNull(vipShopCallbackRequest)){
            vipShopResponse.setCode(-1);
            vipShopResponse.setMsg("fail");
            return vipShopResponse;
        }
        return vipShopService.callbackData(vipShopCallbackRequest);
    }

    /**
     * 唯品维保回调 --测试
     * @param data
     * @return
     */
    @PostMapping("/weibao/callbackDatamy")
    @ApiOperation("唯品维保回调")
    public VipShopResponse callbackDataMy(String data){
        VipShopResponse vipShopResponse = new VipShopResponse();
        if(Objects.isNull(data)){
            vipShopResponse.setCode(-1);
            vipShopResponse.setMsg("fail");
            return vipShopResponse;
        }
        vipShopResponse.setCode(0);
        vipShopResponse.setMsg("success");
        return vipShopResponse;
    }

}
