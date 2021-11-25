package com.wb.bench.service.Impl;

import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.RechargeRecord;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.RechargeRecordMapper;
import com.wb.bench.request.RechargeRequest;
import com.wb.bench.service.CustomerInfoServer;
import com.wb.bench.service.RechargeService;
import com.wb.bench.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class RechargeServiceImpl implements RechargeService {
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private CustomerInfoServer customerInfoServer;

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public void recharge(RechargeRequest request) {
        CustomerInfo customerInfo = customerInfoMapper.selectById(request.getCustomerId());
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1001,"参数错误，未找到相应客户！");
        }
        rechargeRecordMapper.insert(KsBeanUtil.convert(request, RechargeRecord.class));
        customerInfo.setRechargeAmount(customerInfo.getRechargeAmount().add(request.getRechargeAccount()));
        customerInfo.setBalanceAmount(customerInfo.getBalanceAmount().add(request.getRechargeAccount()));
        customerInfoMapper.updateById(customerInfo);
    }
}
