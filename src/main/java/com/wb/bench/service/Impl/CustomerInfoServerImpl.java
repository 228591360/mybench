package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.service.CustomerInfoServer;
import com.wb.bench.util.RedisUtil;
import com.wb.bench.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:15
 */
@Service
public class CustomerInfoServerImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoServer {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<CustomerInfo> queryCustomerInfo() {
        return customerInfoMapper.queryCustomerInfo();
    }

    @Override
    public CustomerInfo queryCustomerInfoById(String customerId) {
        return customerInfoMapper.selectById(customerId);
    }

    @Override
    public int deleteCustomerInfoById(String customerId) {
        return customerInfoMapper.deleteById(customerId);
    }

    @Override
    public Boolean createCustomerInfo(CustomerInfoRequest request) {
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(request,customerInfo);
        if(Objects.nonNull(request.getCustomerId())){
            customerInfo.setCustomerId(Long.valueOf(UUIDUtil.getUUID()));
        }
        return this.saveOrUpdate(customerInfo);
    }
}
