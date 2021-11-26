package com.wb.bench.service;

import com.wb.bench.entity.BasePage;
import com.wb.bench.request.CustomerInfoRequest;
import com.wb.bench.response.CustomerInfoResponse;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:03
 */
public interface CustomerInfoServer {
    BasePage<CustomerInfoResponse> queryPageList(CustomerInfoRequest customerInfoRequest);

    List<CustomerInfoResponse> queryCustomerInfo();

    CustomerInfoResponse queryCustomerInfoById(String customerId);

    int deleteCustomerInfoById(String customerId);

    Boolean createCustomerInfo(CustomerInfoRequest request);
}
