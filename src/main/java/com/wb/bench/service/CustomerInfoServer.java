package com.wb.bench.service;

import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.request.CustomerInfoRequest;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:03
 */
public interface CustomerInfoServer {

    List<CustomerInfo> queryCustomerInfo();

    CustomerInfo queryCustomerInfoById(String customerId);

    int deleteCustomerInfoById(String customerId);

    Boolean createCustomerInfo(CustomerInfoRequest request);
}
