package com.wb.bench.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.ShopBatch;
import com.wb.bench.request.VipShopBRequest;
import com.wb.bench.request.VipShopBatchRequest;
import com.wb.bench.service.VipShopBService;
import com.wb.bench.service.VipShopBatchService;
import com.wb.bench.service.WbQueryLogService;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class VipShopBatchServiceImpl implements VipShopBatchService {

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

    @Autowired
    private VipShopBService vipShopBService;


    @Override
    public String weiBao(VipShopBatchRequest vipShopBatchRequest) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",vipShopBatchRequest.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",vipShopBatchRequest.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1004,"用户未注册");
        }
        if(customerInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new SbcRuntimeException(1005,"余额不足,请充值!");
        }
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",productCode.getVipShopWBCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        List<ShopBatch> shop = vipShopBatchRequest.getShop();
        if(CollectionUtils.isNotEmpty(shop)){
            for (ShopBatch request : shop) {
                LinkedHashMap map = new LinkedHashMap<String ,Object>();
                map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
                Map map1 = new HashMap<String ,String>();
                map1.put("vin",request.getVin());
                map1.put("callback_url","http://139.196.19.64:8082/v2/wb/callback");
                map1.put("licensePlate",request.getLicenseNo());
                map1.put("engine",request.getEngineNo());
                map1.put("imageUrl",request.getLicenseUrl());
                map1.put("regUrl",request.getRegistrationUrl());
                map1.put("carType",request.getCarType());
                map.put("encrypt", JSON.toJSONString(map1));
                map.put("encryptType","false");
                map.put("productCode","BA610033");
                map.put("reqTime",String.valueOf(System.currentTimeMillis()));
                map.put("version","V001");
                String string = map.toString().replace(" ","");
                String endString = string.substring(1, string.length() - 1);
                String sign = (MD5Util.md5Hex(endString, "utf-8"));
                map.put("sign",sign);
                String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);

                JSONObject resultObject = JSONObject.parseObject(end);
                System.out.println("圈讯维保返回结果：======{}"+resultObject);
                String code = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("code").toString();
                if(!"300018".equals(code)){
                    VipShopBRequest vipShopBRequest = BeanUtil.copyProperties(request, VipShopBRequest.class);
                    vipShopBRequest.setCallbackUrl(vipShopBatchRequest.getCallbackUrl());
                    vipShopBRequest.setCustomerId(customerInfo.getCustomerId());
                    vipShopBRequest.setCustomerName(customerInfo.getCustomerName());
                    vipShopBService.weiBao(vipShopBRequest);
                    continue;
                }
                String orderId = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("gid").toString();
                String charge = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("charge").toString();
                String reqTime = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("reqTime").toString();
                WbQueryLog wbQueryLog = new WbQueryLog();
                wbQueryLog.setVin(request.getVin());
                wbQueryLog.setLicenseNo(request.getLicenseNo());
                wbQueryLog.setEngineNo(request.getEngineNo());
                wbQueryLog.setLicenseUrl(request.getLicenseUrl());
                wbQueryLog.setRegistrationUrl(request.getRegistrationUrl());
                wbQueryLog.setCarType(request.getCarType());
                wbQueryLog.setProductId(customerProduct.getProductId());
                wbQueryLog.setProductName("圈讯维保");
                wbQueryLog.setOrderId(orderId);
                wbQueryLog.setCallBackUrl(vipShopBatchRequest.getCallbackUrl());
                wbQueryLog.setCustomerId(customerInfo.getCustomerId());
                wbQueryLog.setCustomerName(customerInfo.getCustomerName());
                wbQueryLog.setToll(charge.equals("false")?"否":"是");
                wbQueryLog.setCreateTime(LocalDateTime.now());
                wbQueryLogMapper.insert(wbQueryLog);
            }
        }

        return "ok";
    }

}
