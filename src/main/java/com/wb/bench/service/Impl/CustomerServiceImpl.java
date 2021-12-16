package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.request.CustomerProductRequest;
import com.wb.bench.response.CustomerProductResponse;
import com.wb.bench.service.CustomerProductService;
import com.wb.bench.util.KsBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerServiceMapper, CustomerProduct> implements CustomerProductService {
    @Resource
    private CustomerServiceMapper customerServiceMapper;

    @Override
    public List<CustomerProductResponse> queryList(CustomerProductRequest customerProductRequest) {
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerProductRequest.getCustomerId());
        List<CustomerProduct> customerProducts = customerServiceMapper.selectList(customerProductQueryWrapper);
       if(CollectionUtils.isEmpty(customerProducts)){
           return null;
       }
       return KsBeanUtil.convert(customerProducts, CustomerProductResponse.class);
    }

    @Override
    public void deleteByCustomerIdAndProductId(CustomerProductRequest customerProductRequest) {
        customerServiceMapper.deleteByCustomerIdAndProductId(customerProductRequest);
    }

    @Override
    public boolean editCustomerProduct(CustomerProductRequest customerProductRequest) {
        CustomerProduct customerService = KsBeanUtil.convert(customerProductRequest, CustomerProduct.class);
        customerService.setCreateTime(LocalDateTime.now());
        return this.saveOrUpdate(customerService);
    }
}
