package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.request.LoginRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.LoginService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public CustomerInfoResponse login(LoginRequest request) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",request.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",request.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new RuntimeException("帐号或密码错误");
        }
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse();
        BeanUtils.copyProperties(customerInfo,customerInfoResponse);
        return customerInfoResponse;
    }
}
