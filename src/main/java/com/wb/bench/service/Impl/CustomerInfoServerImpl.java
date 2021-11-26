package com.wb.bench.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.CustomerInfo;
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
        List<CustomerInfo> customerInfos = customerInfoMapper.queryCustomerInfo();
        List<CustomerInfoResponse> customerInfoResponses = KsBeanUtil.convertList(customerInfos, CustomerInfoResponse.class);
        if (CollectionUtil.isNotEmpty(customerInfoResponses)) {
            PageInfo<CustomerInfoResponse> pageInfo = new PageInfo<>(customerInfoResponses);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(customerInfoResponses);
        }
        return basePage;
    }

    @Override
    public List<CustomerInfoResponse> queryCustomerInfo() {
        List<CustomerInfo> customerInfos = customerInfoMapper.queryCustomerInfo();
        return KsBeanUtil.convertList(customerInfos, CustomerInfoResponse.class);
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
    public Boolean createCustomerInfo(CustomerInfoRequest request) {
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(request,customerInfo);
        if(Objects.nonNull(request.getCustomerId())){
            customerInfo.setCustomerId(Long.valueOf(UUIDUtil.getUUID()));
        }
        return this.saveOrUpdate(customerInfo);
    }
}
