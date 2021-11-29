package com.wb.bench.service;

import com.wb.bench.entity.BasePage;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.request.RechargeRequest;
import com.wb.bench.response.RechargeRecordResponse;

public interface RechargeService {
    void recharge(RechargeRequest request);

    BasePage<RechargeRecordResponse> rechargePageList(CustomerInfoRequest customerInfoRequest);
}
