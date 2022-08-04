package com.wb.bench.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wb.bench.VO.VipShopBVO;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.*;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.response.VipShopResponse;
import com.wb.bench.service.VipShopBService;
import com.wb.bench.service.WbQueryLogService;
import com.wb.bench.util.Base64Util;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import com.wb.bench.util.UnicodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class VipShopBServiceImpl implements VipShopBService {
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

    /**
     * 维保查询
     * @param request
     * @return
     */
    @Override
    public String weiBaoQX(VipShopRequest request) {
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
        customerProductQueryWrapper.eq("product_id",productCode.getVipShopWBCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
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
        map.put("encrypt",JSON.toJSONString(map1));
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
            vipShopBRequest.setCallbackUrl(request.getCallbackUrl());
            vipShopBRequest.setCustomerId(customerInfo.getCustomerId());
            vipShopBRequest.setCustomerName(customerInfo.getCustomerName());
            return weiBao(vipShopBRequest);
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
        wbQueryLog.setCallBackUrl(request.getCallbackUrl());
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll(charge.equals("false")?"否":"是");
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        VipShopBVO vipShopBVO = new VipShopBVO();
        vipShopBVO.setCode("0");
        vipShopBVO.setMsg("success");
        vipShopBVO.setRequestId(reqTime);
        HashMap<String, String> mapNew = new HashMap<>();
        mapNew.put("orderId",orderId);
        mapNew.put("vin",request.getVin());
        vipShopBVO.setData(mapNew);
        return JSON.toJSONString(vipShopBVO);
    }

    @Override
    public OutDangerBackResponse callback(OutDangerBackRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("sign",request.getSign());
        stringObjectHashMap.put("encryptType",request.getEncryptType());
        String replace = UnicodeUtil.unicodeToString(JSON.toJSONString(request.getEncrypt())).replace("\\", "");
        String substring = replace.substring(1, replace.length() - 1);
        String gid = JSONObject.parseObject(substring).get("gid").toString();
        String charge = JSONObject.parseObject(substring).get("charge").toString();
        String code = JSONObject.parseObject(substring).get("code").toString();
        stringObjectHashMap.put("encrypt",substring);
        String json = JSON.toJSONString(stringObjectHashMap);
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",gid);
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
        if(!code.equals("0")){
            VipShopBRequest vipShopBRequest = BeanUtil.copyProperties(wbQueryLog, VipShopBRequest.class);
            vipShopBRequest.setCallbackUrl(wbQueryLog.getCallBackUrl());
            weiBao(vipShopBRequest);
        }else {
            JSONObject data = JSONObject.parseObject(substring).getJSONObject("data");
            data.put("orderId",gid);
            data.remove("report");
            VipShopBCallbackRequest vipShopBCallbackRequest = new VipShopBCallbackRequest();
            vipShopBCallbackRequest.setCode(0);
            vipShopBCallbackRequest.setMsg("success");
            vipShopBCallbackRequest.setData(data);
            byte[] bytes = JSON.toJSONString(vipShopBCallbackRequest).getBytes(StandardCharsets.UTF_8);
            String replaceDecode = java.util.Base64.getEncoder().encodeToString(bytes);
            Map map = new HashMap<String ,Object>();
            map.put("data",replaceDecode);
            String s = HttpClientUtil.doPost(callBackUrl, map);
            System.out.println("圈讯维保：========" +s);
            //保存结果
            UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
            wrapper.set("result", json);
            wrapper.set("back_time",LocalDateTime.now());
            wrapper.eq("order_id", gid);
            wbQueryLogService.update(wrapper);
            //查询成功扣费
            if(charge.equals("true")){
                UpdateWrapper<WbQueryLog> wrapper2 = new UpdateWrapper<>();
                wrapper2.set("toll", "是");
                wrapper2.set("back_time",LocalDateTime.now());
                wrapper2.eq("order_id", gid);
                wbQueryLogService.update(wrapper2);
                deduction(wbQueryLog.getCustomerId(),productCode.getYiBuChuXianCode());
            }
        }
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        outDangerBackResponse.setCode(0);
        return outDangerBackResponse;
    }

    @Override
    public String weiBao(VipShopBRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vin", request.getVin());
        jsonObject.put("callbackUrl", "http://139.196.19.64:8082/v2/callback");
        jsonObject.put("licenseNo", request.getLicenseNo());
        jsonObject.put("licenseUrl", request.getLicenseUrl());
        jsonObject.put("registrationUrl", request.getRegistrationUrl());
        jsonObject.put("engineNo", request.getEngineNo());
        jsonObject.put("carType", request.getCarType());

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
        wbQueryLog.setVin(request.getVin());
        wbQueryLog.setProductId(productCode.getVipShopWBCode());
        wbQueryLog.setProductName("唯品维保");
        wbQueryLog.setOrderId(orderId);
        wbQueryLog.setCallBackUrl(request.getCallbackUrl());
        wbQueryLog.setCustomerId(request.getCustomerId());
        wbQueryLog.setCustomerName(request.getCustomerName());
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
