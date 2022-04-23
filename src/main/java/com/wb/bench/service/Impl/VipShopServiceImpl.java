package com.wb.bench.service.Impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.VipShopCallbackRequest;
import com.wb.bench.request.VipShopRequest;
import com.wb.bench.response.VipShopResponse;
import com.wb.bench.service.VipShopService;
import com.wb.bench.service.WbQueryLogService;
import com.wb.bench.util.Base64Util;
import com.wb.bench.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class VipShopServiceImpl implements VipShopService {
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
    @Test
    public void test() {
        System.out.println(getAuht());
        createOrder();
    }



    @Override
    public String weiBao(VipShopRequest vipShopRequest) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",vipShopRequest.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",vipShopRequest.getCustomerPassword());
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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vin", vipShopRequest.getVin());
        jsonObject.put("callbackUrl", "http://139.196.19.64/api/weibao/callback");
        jsonObject.put("licenseNo", vipShopRequest.getLicenseNo());
        jsonObject.put("licenseUrl", vipShopRequest.getLicenseUrl());
        jsonObject.put("registrationUrl", vipShopRequest.getRegistrationUrl());
        jsonObject.put("engineNo", vipShopRequest.getEngineNo());
        jsonObject.put("carType", vipShopRequest.getCarType());

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
        wbQueryLog.setVin(vipShopRequest.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("唯品维保");
        wbQueryLog.setOrderId(orderId);
        wbQueryLog.setCallBackUrl(vipShopRequest.getCallbackUrl());
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll("否");
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        return result;
    }

    @Override
    public VipShopResponse callbackData(VipShopCallbackRequest vipShopCallbackRequest) {
        Integer code = vipShopCallbackRequest.getCode();
        String orderId = vipShopCallbackRequest.getData().getOrderId();
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",orderId);
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
        if(0==code){
            UpdateWrapper<WbQueryLog> wrapper2 = new UpdateWrapper<>();
            wrapper2.set("toll", "是");
            wrapper2.set("back_time",LocalDateTime.now());
            wrapper2.eq("order_id", orderId);
            wbQueryLogService.update(wrapper2);
            deduction(wbQueryLog.getCustomerId(),productCode.getVipShopWBCode());
        }
        byte[] bytes = JSON.toJSONString(vipShopCallbackRequest).getBytes(StandardCharsets.UTF_8);
        String replaceDecode = java.util.Base64.getEncoder().encodeToString(bytes);
        Map map = new HashMap<String ,Object>();
        map.put("data",replaceDecode);
        String s = HttpClientUtil.doPost(callBackUrl, map);
        System.out.println("唯品维保回调地址返回数据：========" +s);
        //保存结果
        UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
        wrapper.set("result",Base64Util.decode(replaceDecode));
        wrapper.set("back_time",LocalDateTime.now());
        wrapper.eq("order_id", orderId);
        wbQueryLogService.update(wrapper);
        VipShopResponse vipShopResponse = new VipShopResponse();
        vipShopResponse.setCode(Integer.valueOf(JSONObject.parseObject(s).get("code").toString()));
        vipShopResponse.setMsg(JSONObject.parseObject(s).get("msg").toString());
        return vipShopResponse;
    }

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
}
