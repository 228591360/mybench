package com.wb.bench.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.bench.entity.CustomerService;
import com.wb.bench.request.CustomerServiceRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:02
 */
@Mapper
public interface CustomerServiceMapper extends BaseMapper<CustomerService> {
    void deleteByCustomerIdAndServiceId(CustomerServiceRequest customerServiceRequest);
}
