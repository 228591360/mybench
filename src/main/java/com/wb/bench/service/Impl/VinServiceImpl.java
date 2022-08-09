package com.wb.bench.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.BasePage;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.*;
import com.wb.bench.response.LogResponse;
import com.wb.bench.response.OutDangerBackResponse;
import com.wb.bench.response.StatisticsResponse;
import com.wb.bench.service.VinService;
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
import java.util.*;

@Service
@Slf4j
public class VinServiceImpl implements VinService {
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

    /**
     * 维保查询接口
     * @param vinRequest
     * @return
     */
    @Override
    public String queryVinInfo(VinRequest vinRequest){
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",vinRequest.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",vinRequest.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1004,"用户未注册");
        }
        if(customerInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new SbcRuntimeException(1005,"余额不足,请充值!");
        }
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",vinRequest.getProductId());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        LinkedHashMap map = new LinkedHashMap();
        map.put("callbackUrl",callbackUrl);
        if(Objects.nonNull(vinRequest.getCarNumber())){
            map.put("carNumber",vinRequest.getCarNumber());
        }
        if(Objects.nonNull(vinRequest.getEngine())){
            map.put("engine",vinRequest.getEngine());
        }
        map.put("timeStamp",String.valueOf(System.currentTimeMillis()));
        map.put("userId",userId);
        map.put("userToken",userToken);
        map.put("vin",vinRequest.getVin());
        String string = map.toString().replace(", ", "&");
        String endString = string.substring(1, string.length() - 1) + apiKey;
        String appSign = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("imageUrl",vinRequest.getImageUrl());
        map.put("djzUrl",vinRequest.getDjzUrl());
        map.put("appSign",appSign);
        String s = HttpClientUtil.doPost("http://cc2.thinkingleap.com/car-data/api/query/wb", map);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if("查询成功".equals(jsonObject.get("message"))&&"0".equals(jsonObject.get("code"))){
            //查询成功后添加查询日志
            WbQueryLog wbQueryLog = new WbQueryLog();
            wbQueryLog.setVin(vinRequest.getVin());
            wbQueryLog.setProductId(customerProduct.getProductId());
            wbQueryLog.setProductName("维保");
            wbQueryLog.setOrderId(jsonObject.get("orderid").toString());
            wbQueryLog.setCallBackUrl(vinRequest.getCallbackUrl());
            wbQueryLog.setCustomerId(customerInfo.getCustomerId());
            wbQueryLog.setCustomerName(customerInfo.getCustomerName());
            wbQueryLog.setToll("否");
            wbQueryLog.setCreateTime(LocalDateTime.now());
            wbQueryLogMapper.insert(wbQueryLog);
        }
        return s;
    }

    /**
     * 维保回调接口
     * @param data
     * @return
     */
    @Override
    public String freceivedata(String data) {
        byte[] result = Base64.getDecoder().decode(data.getBytes());
        String s = new String(result);
        JSONObject jsonObject = JSONObject.parseObject(s);
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",jsonObject.get("orderid").toString());
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
        String customerId = wbQueryLog.getCustomerId();
        if("1001".equals(jsonObject.get("code").toString())){
            byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
            String replaceDecode = Base64.getEncoder().encodeToString(bytes);
            Map map = new HashMap<String ,Object>();
            map.put("data",replaceDecode);
            HttpClientUtil.doPost(callBackUrl, map);
            //保存查询结果
            UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
            wrapper.set("back_time",LocalDateTime.now());
            wrapper.set("result", s);
            wrapper.eq("order_id", jsonObject.get("orderid").toString());
            wbQueryLogService.update(wrapper);
            return "success";
        }
        if(!"查询成功".equals(jsonObject.get("message").toString())){
            return "fail";
        }
        String replace = s.replace("wbcl", "material").replace("wblc", "mileage")
                .replace("wbrq", "date").replace("wbxm", "project").replace("wbzl", "category");
        log.info("维保结果查询========{}",replace);
        byte[] bytes = replace.getBytes(StandardCharsets.UTF_8);
        String replaceDecode = Base64.getEncoder().encodeToString(bytes);
        Map map = new HashMap<String ,Object>();
        map.put("data",replaceDecode);
        HttpClientUtil.doPost(callBackUrl, map);
        //保存查询结果
        UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
        wrapper.set("result", replace);
        wrapper.set("back_time",LocalDateTime.now());
        wrapper.eq("order_id", jsonObject.get("orderid").toString());
        wbQueryLogService.update(wrapper);
        //查询成功计费
        if("0".equals(jsonObject.get("code").toString())){
            UpdateWrapper<WbQueryLog> wrapper2 = new UpdateWrapper<>();
            wrapper2.set("toll", "是");
            wrapper2.set("back_time",LocalDateTime.now());
            wrapper2.eq("order_id", jsonObject.get("orderid").toString());
            wbQueryLogService.update(wrapper2);
            deduction(customerId,productCode.getWbCode());
        }
        return "success";
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

    /**
     * 出险查询
     * @param outVinRequest
     * @return
     */
    @Override
    public String outDange(OutVinRequest outVinRequest) {
        QueryWrapper<CustomerInfo> customerInfoQueryWrapper = new QueryWrapper<>();
        customerInfoQueryWrapper.eq("customer_account",outVinRequest.getCustomerAccount());
        customerInfoQueryWrapper.eq("customer_password",outVinRequest.getCustomerPassword());
        CustomerInfo customerInfo = customerInfoMapper.selectOne(customerInfoQueryWrapper);
        if(Objects.isNull(customerInfo)){
            throw new SbcRuntimeException(1004,"用户未注册");
        }
        if(customerInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new SbcRuntimeException(1005,"余额不足,请充值!");
        }
        QueryWrapper<CustomerProduct> customerProductQueryWrapper = new QueryWrapper<>();
        customerProductQueryWrapper.eq("customer_id",customerInfo.getCustomerId());
        customerProductQueryWrapper.eq("product_id",outVinRequest.getProductId());
        CustomerProduct customerProduct = customerServiceMapper.selectOne(customerProductQueryWrapper);
        if(Objects.isNull(customerProduct)){
            throw new SbcRuntimeException(1006,"无权限调用服务");
        }
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin",outVinRequest.getVin());
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
        JSONObject encrypt = JSON.parseObject(end).getJSONObject("encrypt");
        encrypt.put("productCode","danger");
        encrypt.put("version","WAGU001");
        String charge = encrypt.get("charge").toString();
        String toll ="否";
        if("true".equals(charge)){
            toll="是";
        }
        WbQueryLog wbQueryLog = new WbQueryLog();
        wbQueryLog.setVin(outVinRequest.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("出险");
        wbQueryLog.setOrderId("出险查询");
        wbQueryLog.setCallBackUrl("出险查询");
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll(toll);
        wbQueryLog.setResult(end);
        wbQueryLog.setBackTime(LocalDateTime.now());
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        deduction(customerInfo.getCustomerId(),productCode.getChuXianCode());
        return encrypt.toJSONString();
    }

    @Override
    public JSONObject outDanger(OutDangerRequest request) {
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
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin",request.getVin());
        map1.put("callback_url","http://139.196.19.64:8082/outDangerBackData");
        map1.put("engine_number",request.getEngineNumber());
        map1.put("license_no",request.getLicenseNo());
        map1.put("id_number",request.getIdNumber());
        map1.put("province",request.getProvince());
        map1.put("image_url",request.getImageUrl());
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
        JSONObject encrypt = resultObject.getJSONObject("encrypt");
        encrypt.put("productCode","asynchronous");
        encrypt.put("version","WAGU001");
        String orderId = encrypt.get("gid").toString();
        String charge = encrypt.get("charge").toString();
        WbQueryLog wbQueryLog = new WbQueryLog();
        wbQueryLog.setVin(request.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("异步出险");
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
    public OutDangerBackResponse outDangerBackData(OutDangerBackRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("sign",request.getSign());
        stringObjectHashMap.put("encryptType",request.getEncryptType());
        String replace = UnicodeUtil.unicodeToString(JSON.toJSONString(request.getEncrypt())).replace("\\", "");
        String substring = replace.substring(1, replace.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(substring);
        jsonObject.put("productCode","asynchronous");
        jsonObject.put("version","WAGU001");
        String gid = jsonObject.get("gid").toString();
        String charge = jsonObject.get("charge").toString();
        stringObjectHashMap.put("encrypt",jsonObject.toJSONString());
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
        System.out.println("异步出险回调地址返回数据：========" +s);
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
        OutDangerBackResponse outDangerBackResponse = new OutDangerBackResponse();
        outDangerBackResponse.setCode(Integer.valueOf(JSONObject.parseObject(s).get("code").toString()));
        return outDangerBackResponse;
    }

    @Override
    public BasePage<StatisticsResponse> queryPage(StatisticsRequest request) {
        BasePage<StatisticsResponse> basePage = new BasePage<>();
        basePage.setCurrent(request.getPage());
        basePage.setSize(request.getLimit());
        PageHelper.startPage(request.getPage(), request.getLimit());
        List<StatisticsResponse> page = wbQueryLogMapper.queryPage(request);
        if (CollectionUtil.isNotEmpty(page)) {
            PageInfo<StatisticsResponse> pageInfo = new PageInfo<>(page);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(page);
        }
        return basePage;
    }

    @Override
    public BasePage<LogResponse> queryLog(LogRequest request) {
        BasePage<LogResponse> basePage = new BasePage<>();
        basePage.setCurrent(request.getPage());
        basePage.setSize(request.getLimit());
        PageHelper.startPage(request.getPage(), request.getLimit());
        List<LogResponse> page = wbQueryLogMapper.queryLog(request);
        if (CollectionUtil.isNotEmpty(page)) {
            PageInfo<LogResponse> pageInfo = new PageInfo<>(page);
            //统计总条数
            basePage.setTotal(pageInfo.getTotal());
            basePage.setList(page);
        }
        return basePage;
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
