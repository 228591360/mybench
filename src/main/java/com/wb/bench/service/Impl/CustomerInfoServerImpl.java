package com.wb.bench.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.response.CustomerInfoResponse;
import com.wb.bench.service.CustomerInfoServer;
import com.wb.bench.util.KsBeanUtil;
import com.wb.bench.util.RedisUtil;
import com.wb.bench.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    public BasePage<CustomerInfoResponse> queryPageList(CustomerInfoRequest customerInfoRequest) {
        BasePage<CustomerInfoResponse> basePage = new BasePage<>();
        basePage.setCurrent(customerInfoRequest.getPage());
        PageHelper.startPage(customerInfoRequest.getPage(), customerInfoRequest.getLimit());
        List<CustomerInfoResponse> customerInfos = customerInfoMapper.queryCustomerInfo();
        if (CollectionUtil.isNotEmpty(customerInfos)) {
            PageInfo<CustomerInfoResponse> pageInfo = new PageInfo<>(customerInfos);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(customerInfos);
        }
        return basePage;
    }

    @Override
    public List<CustomerInfoResponse> queryCustomerInfo() {
        List<CustomerInfoResponse> customerInfos = customerInfoMapper.queryCustomerInfo();
        return customerInfos;
    }

    @Override
    public CustomerInfoResponse queryCustomerInfoById(String customerId) {
        CustomerInfo customerInfo = customerInfoMapper.selectById(customerId);
        return KsBeanUtil.convert(customerInfo,CustomerInfoResponse.class);
    }

    @Override
    public int deleteCustomerInfoById(String customerId) {
        return customerInfoMapper.deleteById(customerId);
    }

    @Override
    public Boolean editCustomerInfo(CustomerInfoRequest request) {
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(request,customerInfo);
        if(Objects.isNull(request.getCustomerId())){
            QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
            customerInfoQueryWrapper.eq("customer_account",request.getCustomerAccount());
            Integer integer = customerInfoMapper.selectCount(customerInfoQueryWrapper);
            if(integer>0){
                throw new SbcRuntimeException(1001,"客户手机号已注册");
            }
            customerInfo.setCustomerId(UUIDUtil.getUUID());
            customerInfo.setCreateTime(LocalDateTime.now());
        }
        return this.saveOrUpdate(customerInfo);
    }
}
