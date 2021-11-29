package com.wb.bench.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.response.CustomerInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:02
 */
@Mapper
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo> {
    List<CustomerInfoResponse> queryCustomerInfo();
}
