package com.wb.bench.service.Impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
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
import com.wb.bench.request.VipShopBatchRequest;
import com.wb.bench.service.VipShopBatchService;
import com.wb.bench.service.WbQueryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class VipShopBatchServiceImpl implements VipShopBatchService {
    //这里只是示例，分布式环境下可存在redis，mysql等环境中去，根据实际情况处理
    private static long expiresIn = 1636135141263L;
    private static String accessToken = "";

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
     * 获取token 可以防止重复生成token
     * @return
     */
    public String getAuht() {
        //一定时间内不过期就不重新拿了，使用缓存
        if (System.currentTimeMillis() + 100000 >= expiresIn) {
            String result = HttpRequest.post("http://47.92.93.251/vpc/gateway/auth-center/authorize/client?grant_type=client_credentials")
                    .header("Accept", "*/*")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "basic " + getGenericBasicAuth())
                    .execute().body();
            System.out.println(result);
            JSONObject resultJson = JSONObject.parseObject(result);
            expiresIn = resultJson.getJSONObject("data").getLongValue("expires_in");
            accessToken = resultJson.getJSONObject("data").getString("access_token");
            return accessToken;
        }
        return accessToken;
    }

    public String getGenericBasicAuth(){
        return getbasicString("4a43332f9345460ea15fe5c3e3c96254", "7d68954e55ea4a4b85c4f9f102ecbdc5");
    }
    public String getbasicString(String id, String secret) {
        String data = id.concat(":").concat(secret);
        return Base64.encode(data, CharsetUtil.CHARSET_UTF_8);
    }

    public void createOrder(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vin", "vin");
        jsonObject.put("licenseUrl", "");
        jsonObject.put("licenseUrl", "");
        jsonObject.put("licenseUrl", "");
        jsonObject.put("licenseUrl", "");
        jsonObject.put("licenseUrl", "");
        //.. 其他参数
        try {
            Thread.sleep(1000);
        } catch (Exception ex) {

        }
        jsonObject.put("callbackUrl", "回调地址");
        //下单地址
        String result = HttpRequest.post("http://47.92.93.251/vpc/gateway/data-center/order/weibao")
                .body(jsonObject.toJSONString())
                .header("Accept", "*/*")
                .header("Content-Type", "application/json")
                .header("Authorization", getAuht())
                .execute().body();
        //打印结果，下单结果
        System.out.println(result);
    }

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
            for (ShopBatch shopBatch : shop) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("vin", shopBatch.getVin());
                jsonObject.put("callbackUrl", "http://139.196.19.64/api/weibao/callback");
                jsonObject.put("licenseNo", shopBatch.getLicenseNo());
                jsonObject.put("licenseUrl", shopBatch.getLicenseUrl());
                jsonObject.put("registrationUrl", shopBatch.getRegistrationUrl());
                jsonObject.put("engineNo", shopBatch.getEngineNo());
                jsonObject.put("carType", shopBatch.getCarType());

                //下单地址
                String result = HttpRequest.post("http://47.92.93.251/vpc/gateway/data-center/order/weibao")
                        .body(jsonObject.toJSONString())
                        .header("Accept", "*/*")
                        .header("Content-Type", "application/json")
                        .header("Authorization", getAuht())
                        .execute().body();
                //打印结果，下单结果
                System.out.println(result);

                JSONObject resultObject = JSONObject.parseObject(result);
                String orderId = JSONObject.parseObject(resultObject.get("data").toString()).get("orderId").toString();
                WbQueryLog wbQueryLog = new WbQueryLog();
                wbQueryLog.setVin(shopBatch.getVin());
                wbQueryLog.setProductId(customerProduct.getProductId());
                wbQueryLog.setProductName("唯品维保");
                wbQueryLog.setOrderId(orderId);
                wbQueryLog.setCallBackUrl(vipShopBatchRequest.getCallbackUrl());
                wbQueryLog.setCustomerId(customerInfo.getCustomerId());
                wbQueryLog.setCustomerName(customerInfo.getCustomerName());
                wbQueryLog.setToll("否");
                wbQueryLog.setCreateTime(LocalDateTime.now());
                wbQueryLogMapper.insert(wbQueryLog);
            }
        }

        return "ok";
    }

}
