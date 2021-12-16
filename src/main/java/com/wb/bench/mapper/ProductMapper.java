package com.wb.bench.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.bench.entity.Product;
import com.wb.bench.request.ProductRequest;
import com.wb.bench.response.ProductResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:02
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<ProductResponse> queryProduct(ProductRequest productRequest);
}
