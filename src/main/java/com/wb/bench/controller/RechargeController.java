package com.wb.bench.controller;

import com.wb.bench.base.BaseResponse;
import com.wb.bench.exception.BaseBusinessException;
import com.wb.bench.request.RechargeRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.RechargeService;
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
    public BaseResponse<CustomerInfoResponse> recharge(@RequestBody @Validated RechargeRequest request)throws BaseBusinessException {
        rechargeService.recharge(request);
        return BaseResponse.SUCCESSFUL();
    }
}
