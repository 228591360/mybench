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
import com.wb.bench.request.InquireRequest;
import com.wb.bench.request.PlaceAnOrderRequest;
import com.wb.bench.service.DangerCService;
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


    public static void main(String[] args) throws Exception {
//        String mvTrackId ="20170926105632_VehicleInsuranceInfo_zhongpuweixin_sa23jhfu";
//        Map map = new HashMap<String ,Object>();
//        map.put("loginName","zhongpuweixin");
//        map.put("pwd","zhongpuweixin1205");
//        map.put("serviceName","VehicleInsuranceInfo");
//        Map map1 = new HashMap<String ,String>();
//        map1.put("vin","LVSHFFAC8EF840063");
//        map.put("param",map1);
//        String jsonString = JSON.toJSONString(map);
//        System.out.println(jsonString);
//        String s = HttpClientUtil.doPostJson("https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo", jsonString,mvTrackId);
//        System.out.println(s);


//        LinkedHashMap map = new LinkedHashMap();
//        map.put("callbackUrl","http://139.196.19.64:8082/freceivedata");
//        map.put("carNumber","null");
//        map.put("engine","null");
//        map.put("timeStamp",String.valueOf(System.currentTimeMillis()));
//        map.put("userId","skHl8OwQONOovA6X");
//        map.put("userToken","VqFel0WgTEMoPX0LWsP5i86FGq9BKD5j");
//        map.put("vin","LVSHFFAC8EF840063");
//        String string = map.toString().replace(", ", "&");
//        System.out.println(string);
//        String endString = string.substring(1, string.length() - 1) + "jkdbx3wdkpAeNKE5Ci7Nvr4j4Q3UAJdn";
//        System.out.println(endString);
//        String appSign = (MD5Util.md5Hex(endString, "utf-8"));
//        System.out.println(appSign);
//        map.put("imageUrl",null);
//        map.put("djzUrl",null);
//        map.put("appSign",appSign);
//        String s = HttpClientUtil.doPost("http://cc2.thinkingleap.com/car-data/api/query/wb", map);
//        System.out.println(s);

//        LinkedHashMap map = new LinkedHashMap<String ,Object>();
//        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
//        Map map1 = new HashMap<String ,String>();
//        map1.put("vin","LVSHFFAC8EF840063");
//        map.put("encrypt",JSON.toJSONString(map1));
//        map.put("encryptType","false");
//        map.put("productCode","BA610011");
//        map.put("reqTime",String.valueOf(System.currentTimeMillis()));
//        map.put("version","V001");
//        String string = map.toString().replace(" ","");
//        String endString = string.substring(1, string.length() - 1);
//        System.out.println(endString);
//        String sign = (MD5Util.md5Hex(endString, "utf-8"));
//        System.out.println(sign);
//        map.put("sign",sign);
//        System.out.println(map);
//        String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
//        System.out.println(JSON.parse(end));
//        JSONObject jsonObject = JSON.parseObject(end);
//        System.out.println(jsonObject.get("encrypt"));
//        System.out.println(JSON.parseObject(jsonObject.get("encrypt").toString()).get("charge"));

        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin","LVSHFFAC8EF840063");
        map1.put("callback_url","http://139.196.19.64:8088/outDangerBackData");
        map1.put("engine_number","");
        map1.put("license_no","");
        map1.put("id_number","");
        map1.put("province","");
        map.put("encrypt",JSON.toJSONString(map1));
        map.put("encryptType","false");
        map.put("productCode","BA610010");
        map.put("reqTime",String.valueOf(System.currentTimeMillis()));
        map.put("version","V002");
        String string = map.toString().replace(" ","");
        String endString = string.substring(1, string.length() - 1);
        String sign = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("sign",sign);
        System.out.println(map);
        String end = HttpClientUtil.doPost("https://entapi.qucent.cn/api/v3", map);
        log.info("异步出险查询结果===={}",end);
        System.out.println(JSONObject.parseObject(JSONObject.parseObject(end).get("encrypt").toString()).get("gid"));
    }
}
