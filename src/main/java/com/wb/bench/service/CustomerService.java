package com.wb.bench.service;

import com.wb.bench.request.CustomerServiceRequest;
import com.wb.bench.response.CustomerServiceResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerServiceResponse> queryList(CustomerServiceRequest customerServiceRequest);

    void deleteByCustomerIdAndServiceId(CustomerServiceRequest customerServiceRequest);

    int create(CustomerServiceRequest customerServiceRequest);
}
