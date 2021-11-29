package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.BasePage;
import com.wb.bench.exception.BaseBusinessException;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.request.RechargeRequest;
import com.wb.bench.response.RechargeRecordResponse;
import com.wb.bench.service.RechargeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RechargeController {
    @Resource
    private RechargeService rechargeService;

    /**
     * 充值
     * @param request
     * @return
     * @throws BaseBusinessException
     */
    @PostMapping("/recharge")
    public Result recharge(@RequestBody @Validated RechargeRequest request){
        rechargeService.recharge(request);
        return R.ok();
    }

    @ApiOperation("充值记录列表分页")
    @PostMapping(value = "/rechargePageList")
    public Result<BasePage<RechargeRecordResponse>> rechargePageList(@RequestBody @Validated CustomerInfoRequest customerInfoRequest){
        BasePage<RechargeRecordResponse> rechargeRecordResponseBasePage = rechargeService.rechargePageList(customerInfoRequest);
        return R.ok(rechargeRecordResponseBasePage);
    }
}
