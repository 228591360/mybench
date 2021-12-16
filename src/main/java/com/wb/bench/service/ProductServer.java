package com.wb.bench.service;

import com.wb.bench.entity.BasePage;
import com.wb.bench.request.ProductRequest;
import com.wb.bench.response.ProductResponse;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:03
 */
public interface ProductServer {

    BasePage<ProductResponse> queryPageList(ProductRequest productRequest);

    int deleteById(String id);

    boolean edit(ProductRequest productRequest);
}
