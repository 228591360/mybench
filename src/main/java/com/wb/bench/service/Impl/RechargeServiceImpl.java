package com.wb.bench.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.RechargeRecord;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.RechargeRecordMapper;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.request.RechargeRequest;
import com.wb.bench.response.RechargeRecordResponse;
import com.wb.bench.service.CustomerInfoServer;
import com.wb.bench.service.RechargeService;
import com.wb.bench.util.KsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
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
        RechargeRecord rechargeRecord = KsBeanUtil.convert(request, RechargeRecord.class);
        rechargeRecord.setRechargeTime(LocalDateTime.now());
        rechargeRecordMapper.insert(rechargeRecord);
        customerInfo.setRechargeAmount(customerInfo.getRechargeAmount().add(request.getRechargeAccount()));
        customerInfo.setBalanceAmount(customerInfo.getBalanceAmount().add(request.getRechargeAccount()));
        customerInfoMapper.updateById(customerInfo);
    }

    @Override
    public BasePage<RechargeRecordResponse> rechargePageList(CustomerInfoRequest customerInfoRequest) {
        BasePage<RechargeRecordResponse> basePage = new BasePage<>();
        basePage.setCurrent(customerInfoRequest.getPage());
        PageHelper.startPage(customerInfoRequest.getPage(), customerInfoRequest.getLimit());
        QueryWrapper<RechargeRecord> rechargeRecordQueryWrapper = new QueryWrapper<>();
        rechargeRecordQueryWrapper.eq("customer_id",customerInfoRequest.getCustomerId());
        List<RechargeRecord> rechargeRecords = rechargeRecordMapper.selectList(rechargeRecordQueryWrapper);
        List<RechargeRecordResponse> convert = null;
        if(CollectionUtil.isNotEmpty(rechargeRecords)){
            convert = KsBeanUtil.convert(rechargeRecords, RechargeRecordResponse.class);
        }
        if (CollectionUtil.isNotEmpty(rechargeRecords)) {
            PageInfo<RechargeRecordResponse> pageInfo = new PageInfo<>(convert);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(convert);
        }
        return basePage;
    }
}
