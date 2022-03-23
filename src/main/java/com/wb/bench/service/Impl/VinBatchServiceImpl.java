package com.wb.bench.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.Danger;
import com.wb.bench.request.OutDangerBatchRequest;
import com.wb.bench.request.OutVinBatchRequest;
import com.wb.bench.service.VinBatchService;
import com.wb.bench.service.WbQueryLogService;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class VinBatchServiceImpl implements VinBatchService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private CustomerServiceMapper customerServiceMapper;

    @Autowired
    private WbQueryLogMapper wbQueryLogMapper;

    @Autowired
    private WbQueryLogService wbQueryLogService;

    @Autowired
    private ProductCode productCode;




    /**
     * 扣费逻辑
     * @param customerId
     */
    @Async
    public void deduction(String customerId,String code) {
        CustomerInfo customerInfo = customerInfoMapper.selectById(customerId);
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",code);
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        customerInfo.setBalanceAmount(customerInfo.getBalanceAmount().subtract(customerProduct.getProductPrice()));
        customerInfoMapper.updateById(customerInfo);
    }

    /**
     * 出险查询
     * @param outVinRequest
     * @return
     */
    @Override
    public String outDange(OutVinBatchRequest request) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",request.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",request.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1004,"用户未注册");
        }
        if(customerInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new SbcRuntimeException(1005,"余额不足,请充值!");
        }
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",request.getProductId());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        for (String s : request.getVin()) {
            LinkedHashMap map = new LinkedHashMap<String ,Object>();
            map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
            Map map1 = new HashMap<String ,String>();
            map1.put("vin",s);
            map.put("encrypt",JSON.toJSONString(map1));
            map.put("encryptType","false");
            map.put("productCode","BA610011");
            map.put("reqTime",String.valueOf(System.currentTimeMillis()));
            map.put("version","V001");
            String string = map.toString().replace(" ","");
            String endString = string.substring(1, string.length() - 1);
            String sign = (MD5Util.md5Hex(endString, "utf-8"));
            map.put("sign",sign);
            String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
            log.info("出险查询结果===={}",end);
            JSONObject jsonObject = JSON.parseObject(end);
            String charge = JSON.parseObject(jsonObject.get("encrypt").toString()).get("charge").toString();
            String toll ="否";
            if("true".equals(charge)){
                toll="是";
            }
            WbQueryLog wbQueryLog = new WbQueryLog();
            wbQueryLog.setVin(s);
            wbQueryLog.setProductId(customerProduct.getProductId());
            wbQueryLog.setProductName("出险");
            wbQueryLog.setOrderId("出险查询");
            wbQueryLog.setCallBackUrl("出险查询");
            wbQueryLog.setCustomerId(customerInfo.getCustomerId());
            wbQueryLog.setCustomerName(customerInfo.getCustomerName());
            wbQueryLog.setToll(toll);
            wbQueryLog.setResult(end);
            wbQueryLog.setCreateTime(LocalDateTime.now());
            wbQueryLogMapper.insert(wbQueryLog);
            deduction(customerInfo.getCustomerId(),productCode.getChuXianCode());
        }

        return "end";
    }

    @Override
    public JSONObject outDanger(OutDangerBatchRequest request) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",request.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",request.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1004,"用户未注册");
        }
        if(customerInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new SbcRuntimeException(1005,"余额不足,请充值!");
        }
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",productCode.getYiBuChuXianCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        for (Danger danger : request.getDangers()) {
            LinkedHashMap map = new LinkedHashMap<String ,Object>();
            map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
            Map map1 = new HashMap<String ,String>();
            map1.put("vin",danger.getVin());
            map1.put("callback_url","http://139.196.19.64:8082/outDangerBackData");
            map1.put("engine_number",danger.getEngineNumber());
            map1.put("license_no",danger.getLicenseNo());
            map1.put("id_number",danger.getIdNumber());
            map1.put("province",danger.getProvince());
            map.put("encrypt",JSON.toJSONString(map1));
            map.put("encryptType","false");
            map.put("productCode","BA610010");
            map.put("reqTime",String.valueOf(System.currentTimeMillis()));
            map.put("version","V002");
            String string = map.toString().replace(" ","");
            String endString = string.substring(1, string.length() - 1);
            String sign = (MD5Util.md5Hex(endString, "utf-8"));
            map.put("sign",sign);
            String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
            log.info("异步出险查询结果===={}",end);

            JSONObject resultObject = JSONObject.parseObject(end);
            String orderId = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("gid").toString();
            String charge = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("charge").toString();
            WbQueryLog wbQueryLog = new WbQueryLog();
            wbQueryLog.setVin(danger.getVin());
            wbQueryLog.setProductId(customerProduct.getProductId());
            wbQueryLog.setProductName("异步出险");
            wbQueryLog.setOrderId(orderId);
            wbQueryLog.setCallBackUrl(request.getCallbackUrl());
            wbQueryLog.setCustomerId(customerInfo.getCustomerId());
            wbQueryLog.setCustomerName(customerInfo.getCustomerName());
            wbQueryLog.setToll(charge.equals("false")?"否":"是");
            wbQueryLog.setCreateTime(LocalDateTime.now());
            wbQueryLogMapper.insert(wbQueryLog);
        }

        return null;
    }


}
