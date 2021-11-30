package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.CustomerService;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.request.CustomerServiceRequest;
import com.wb.bench.response.CustomerServiceResponse;
import com.wb.bench.util.KsBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerService> implements com.wb.bench.service.CustomerService {
    @Resource
    private CustomerServiceMapper customerServiceMapper;

    @Override
    public List<CustomerServiceResponse> queryList(CustomerServiceRequest customerServiceRequest) {
        QueryWrapper<com.wb.bench.entity.CustomerService> customerServiceQueryWrapper = new QueryWrapper<>();
        customerServiceQueryWrapper.eq("customer_id",customerServiceRequest.getCustomerId());
        List<com.wb.bench.entity.CustomerService> customerServices = customerServiceMapper.selectList(customerServiceQueryWrapper);
       if(CollectionUtils.isEmpty(customerServices)){
           return null;
       }
       return KsBeanUtil.convert(customerServices,CustomerServiceResponse.class);
    }

    @Override
    public void deleteByCustomerIdAndServiceId(CustomerServiceRequest customerServiceRequest) {
        customerServiceMapper.deleteByCustomerIdAndServiceId(customerServiceRequest);
    }

    @Override
    public boolean editCustomerService(CustomerServiceRequest customerServiceRequest) {
        com.wb.bench.entity.CustomerService customerService = KsBeanUtil.convert(customerServiceRequest, com.wb.bench.entity.CustomerService.class);
        customerService.setCreateTime(LocalDateTime.now());
        return this.saveOrUpdate(customerService);
    }
}
