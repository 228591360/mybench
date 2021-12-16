package com.wb.bench.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.Product;
import com.wb.bench.mapper.ProductMapper;
import com.wb.bench.request.ProductRequest;
import com.wb.bench.response.ProductResponse;
import com.wb.bench.service.ProductServer;
import com.wb.bench.util.UUIDUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:15
 */
@Service
public class ProductServerImpl extends ServiceImpl<ProductMapper, Product> implements ProductServer {

    @Resource
    private ProductMapper productMapper;

    @Override
    public BasePage<ProductResponse> queryPageList(ProductRequest productRequest) {
        BasePage<ProductResponse> basePage = new BasePage<>();
        basePage.setCurrent(productRequest.getPage());
        basePage.setSize(productRequest.getLimit());
        PageHelper.startPage(productRequest.getPage(), productRequest.getLimit());
        List<ProductResponse> productResponseList = productMapper.queryProduct(productRequest);
        if (CollectionUtil.isNotEmpty(productResponseList)) {
            PageInfo<ProductResponse> pageInfo = new PageInfo<>(productResponseList);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(productResponseList);
        }
        return basePage;
    }

    @Override
    public int deleteById(String id) {
        return productMapper.deleteById(id);
    }

    @Override
    public boolean edit(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest,product);
        if(Objects.isNull(productRequest.getId())){
            product.setId(UUIDUtil.getUUID());
            product.setCreateTime(LocalDateTime.now());
        }
        return this.saveOrUpdate(product);
    }
}
