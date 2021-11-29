package com.wb.bench.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerService;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.request.VinRequest;
import com.wb.bench.service.VinService;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class VinServiceImpl implements VinService {
    final String URL ="https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo";
    final String customerId = "cddd****************087475801189";
    final String QXURL ="entapiceshi.qucent.cn/api";

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private CustomerServiceMapper customerServiceMapper;

    @Override
    public String queryInfo(VinRequest vinRequest) throws Exception {
        String mvTrackId ="20170926105632_VehicleInsuranceInfo_zhongpuweixin_sa23jhfu";
        Map map = new HashMap<String ,Object>();
        map.put("loginName","zhongpuweixin");
        map.put("pwd","zhongpuweixin1205");
        map.put("serviceName","VehicleInsuranceInfo");
        Map vinMap = new HashMap<String ,String>();
        vinMap.put("vin",vinRequest.getVin());
        map.put("param",vinMap);
        String jsonString = JSON.toJSONString(map);
        return  HttpClientUtil.doPostJson(URL, jsonString,mvTrackId);
    }

    @Override
    public String queryVinInfo(VinRequest vinRequest){
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",vinRequest.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",vinRequest.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException("客户未注册");
        }
        QueryWrapper<CustomerService> customerServiceQueryWrapper = new QueryWrapper<>();
        customerServiceQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerServiceQueryWrapper.eq("service_id",vinRequest.getServiceId());
        if(customerServiceMapper.selectCount(customerServiceQueryWrapper)<0){
            throw new SbcRuntimeException("无权限调用服务");
        }
        Map map = new LinkedHashMap<String ,Object>();
        map.put("customerId",customerId);
        Map vinMap = new HashMap<String ,String>();
        vinMap.put("vin",vinRequest.getVin());
        map.put("encrypt",JSON.toJSONString(vinMap));
        map.put("encryptType","false");
        map.put("productCode","BA610011");
        map.put("encryptType","false");
        map.put("reqTime", System.currentTimeMillis());
        map.put("version","V001");
        String sign = (MD5Util.md5Hex(map.toString(), "utf-8"));
        map.put("sign",sign);
        System.out.println(map);
        return null;
    }

    public static void main(String[] args) throws Exception {
        String mvTrackId ="20170926105632_VehicleInsuranceInfo_zhongpuweixin_sa23jhfu";
        Map map = new HashMap<String ,Object>();
        map.put("loginName","zhongpuweixin");
        map.put("pwd","zhongpuweixin1205");
        map.put("serviceName","VehicleInsuranceInfo");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin","LHGCR1657E8041428");
        map.put("param",map1);
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);
        String s = HttpClientUtil.doPostJson("https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo", jsonString,mvTrackId);
        System.out.println(s);
/*
        Map map = new LinkedHashMap<String ,Object>();
        map.put("customerId","customerId");
        Map vinMap = new HashMap<String ,String>();
        vinMap.put("vin","LHGCR1657E8041428");
        map.put("encrypt",JSON.toJSONString(vinMap));
        map.put("encryptType","false");
        map.put("productCode","BA610011");
        map.put("encryptType","false");
        map.put("reqTime", System.currentTimeMillis());
        map.put("version","V001");
        String sign = (MD5Util.md5Hex(map.toString(), "utf-8"));
        map.put("sign",sign);
        System.out.println(map);
        String s = HttpClientUtil.doPostJson("https://host/v3/entapi.qucent.cn/api", map.toString(),null);
        System.out.println(s);*/
    }
}
