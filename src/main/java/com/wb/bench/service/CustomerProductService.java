package com.wb.bench.service;

import com.wb.bench.request.CustomerProductRequest;
import com.wb.bench.response.CustomerProductResponse;

import java.util.List;

public interface CustomerProductService {
    List<CustomerProductResponse> queryList(CustomerProductRequest customerProductRequest);

    void deleteByCustomerIdAndProductId(CustomerProductRequest customerProductRequest);

    boolean editCustomerProduct(CustomerProductRequest customerProductRequest);
}
