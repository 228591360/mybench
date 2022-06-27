package com.wb.bench.service.Impl;

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
import com.wb.bench.request.DangerCRequest;
import com.wb.bench.request.InquireRequest;
import com.wb.bench.request.OutDangerBackRequest;
import com.wb.bench.request.PlaceAnOrderRequest;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.service.DangerCService;
import com.wb.bench.service.WbQueryLogService;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import com.wb.bench.util.UnicodeUtil;
import lombok.extern.slf4j.Slf4j;
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
public class DangerCServiceImpl implements DangerCService {
    final String URL ="https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo";
    final String callbackUrl = "http://139.196.19.64:8082/freceivedata";
    final String userId = "skHl8OwQONOovA6X";
    final String userToken = "VqFel0WgTEMoPX0LWsP5i86FGq9BKD5j";
    final String apiKey = "jkdbx3wdkpAeNKE5Ci7Nvr4j4Q3UAJdn";
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


    @Override
    public JSONObject inquire(InquireRequest request) {
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
        customerProductQueryWrapper.eq("product_id",productCode.getChuXianCCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",request.getOrderId());
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        if(Objects.isNull(wbQueryLog)){
            throw new SbcRuntimeException(1007,"请核对下单id");
        }
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin",request.getVin());
        map1.put("imageUrl",request.getImageUrl());
        map1.put("licenseNo",request.getLicenseNo());
        map1.put("orderId",request.getOrderId());
        map.put("encrypt",JSON.toJSONString(map1));
        map.put("encryptType","false");
        map.put("productCode","BA610014");
        map.put("reqTime",String.valueOf(System.currentTimeMillis()));
        map.put("version","V001");
        String string = map.toString().replace(" ","");
        String endString = string.substring(1, string.length() - 1);
        String sign = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("sign",sign);
        String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
        log.info("出险信息详版查询结果===={}",end);

        JSONObject resultObject = JSONObject.parseObject(end);
        //保存结果
        UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
        wrapper.set("result", end);
        wrapper.set("back_time",LocalDateTime.now());
        wrapper.eq("order_id", request.getOrderId());
        wbQueryLogService.update(wrapper);
        return resultObject;
    }



    @Override
    public JSONObject placeAnOrder(PlaceAnOrderRequest request) {
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
        customerProductQueryWrapper.eq("product_id",productCode.getChuXianCCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin",request.getVin());
        map1.put("imageUrl",request.getImageUrl());
        map1.put("licenseNo",request.getLicenseNo());
        map.put("encrypt",JSON.toJSONString(map1));
        map.put("encryptType","false");
        map.put("productCode","BA610013");
        map.put("reqTime",String.valueOf(System.currentTimeMillis()));
        map.put("version","V001");
        String string = map.toString().replace(" ","");
        String endString = string.substring(1, string.length() - 1);
        String sign = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("sign",sign);
        String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
        log.info("出险信息详版下单结果===={}",end);
        JSONObject jsonObject = JSON.parseObject(end);
        JSONObject encrypt = JSON.parseObject(jsonObject.get("encrypt").toString());
        String code = encrypt.get("code").toString();
        String orderId ="";
        if("0".equals(code)){
            String data = encrypt.get("data").toString();
            orderId = JSON.parseObject(data).get("orderId").toString();
        }
        String charge = JSONObject.parseObject(jsonObject.get("encrypt").toString()).get("charge").toString();
        String toll="否";
        if("true".equals(charge)){
            toll="是";
            deduction(customerInfo.getCustomerId(),productCode.getChuXianCCode());
        }
        WbQueryLog wbQueryLog = new WbQueryLog();
        wbQueryLog.setVin(request.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("出险信息详版");
        wbQueryLog.setOrderId(orderId);
        wbQueryLog.setCallBackUrl("出险信息详版");
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll(toll);
        wbQueryLog.setResult(end);
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        return JSON.parseObject(end);
    }

    @Override
    public JSONObject query(DangerCRequest request) {
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
        customerProductQueryWrapper.eq("product_id",productCode.getChuXianCCode());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin",request.getVin());
        map1.put("callback_url","http://139.196.19.64:8082/queryBackData");
        map1.put("imageUrl",request.getImageUrl());
        map1.put("licenseNo",request.getLicenseNo());
        map.put("encrypt",JSON.toJSONString(map1));
        map.put("encryptType","false");
        map.put("productCode","BA610015");
        map.put("reqTime",String.valueOf(System.currentTimeMillis()));
        map.put("version","V001");
        String string = map.toString().replace(" ","");
        String endString = string.substring(1, string.length() - 1);
        String sign = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("sign",sign);
        String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
        log.info("出险信息详版下单结果===={}",end);

        JSONObject resultObject = JSONObject.parseObject(end);
        String orderId = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("gid").toString();
        String charge = JSONObject.parseObject(resultObject.get("encrypt").toString()).get("charge").toString();
        WbQueryLog wbQueryLog = new WbQueryLog();
        wbQueryLog.setVin(request.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("出险信息详版");
        wbQueryLog.setOrderId(orderId);
        wbQueryLog.setCallBackUrl(request.getCallbackUrl());
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll(charge.equals("false")?"否":"是");
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        return resultObject;
    }

    @Override
    public OutDangerBackResponse queryBackData(OutDangerBackRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("sign",request.getSign());
        stringObjectHashMap.put("encryptType",request.getEncryptType());
        String replace = UnicodeUtil.unicodeToString(JSON.toJSONString(request.getEncrypt())).replace("\\", "");
        String substring = replace.substring(1, replace.length() - 1);
        String gid = JSONObject.parseObject(substring).get("gid").toString();
        String charge = JSONObject.parseObject(substring).get("charge").toString();
        stringObjectHashMap.put("encrypt",substring);
        String json = JSON.toJSONString(stringObjectHashMap);
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",gid);
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        String replaceDecode = java.util.Base64.getEncoder().encodeToString(bytes);
        Map map = new HashMap<String ,Object>();
        map.put("data",replaceDecode);
        String s = HttpClientUtil.doPost(callBackUrl, map);
        System.out.println("出险信息详版回调返回数据：========" +s);
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
            deduction(wbQueryLog.getCustomerId(),productCode.getChuXianCCode());
        }
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        outDangerBackResponse.setCode(Integer.valueOf(JSONObject.parseObject(s).get("code").toString()));
        return outDangerBackResponse;
    }

}
