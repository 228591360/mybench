package com.wb.bench.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wb.bench.VO.DangerCVO;
import com.wb.bench.common.ProductCode;
import com.wb.bench.entity.CustomerInfo;
import com.wb.bench.entity.CustomerProduct;
import com.wb.bench.entity.WbQueryLog;
import com.wb.bench.exception.SbcRuntimeException;
import com.wb.bench.mapper.CustomerInfoMapper;
import com.wb.bench.mapper.CustomerServiceMapper;
import com.wb.bench.mapper.WbQueryLogMapper;
import com.wb.bench.request.DangerCRequest;
import com.wb.bench.request.OutDangerBackRequest;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        map1.put("callbackUrl","http://139.196.19.64:8082/dangerC/queryBackData");
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
        JSONObject encrypt = resultObject.getJSONObject("encrypt");
        encrypt.put("productCode","accurate");
        encrypt.put("version","WAGU001");
        String orderId = encrypt.get("gid").toString();
        String charge = encrypt.get("charge").toString();
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
        JSONObject jsonObject = JSONObject.parseObject(substring);
        jsonObject.put("productCode","accurate");
        jsonObject.put("version","WAGU001");
        String gid = jsonObject.get("gid").toString();
        String charge = jsonObject.get("charge").toString();
        stringObjectHashMap.put("encrypt",substring);
        String json = JSON.toJSONString(stringObjectHashMap);
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",gid);
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
//        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
//        String replaceDecode = java.util.Base64.getEncoder().encodeToString(bytes);
        Map map = new HashMap<String ,Object>();
        map.put("data",json);
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


    @Override
    public JSONObject query2(DangerCRequest request) {
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        LinkedHashMap map = new LinkedHashMap<String ,Object>();
        map.put("customerId","822857ee-7f94-4b2a-9b26-7fe29f139617");
        map.put("appSecret","f033780d-7c3c-4f3b-a7a6-f87dbc926385");
        map.put("vin",request.getVin());
        map.put("carNo",request.getLicenseNo());
        map.put("suppliers",Arrays.asList(12));
        map.put("date",format);
        map.put("callbackUrl","http://139.196.19.64:8088/dangerC/queryBackData2");
        String string = map.toString().replace(", ","&").replace("[","").replace("]","");
        String endString = string.substring(1, string.length() - 1);
        String sn = (MD5Util.md5Hex(endString, "utf-8"));
        map.put("sn",sn);
        //生成签名后删除无用字段传入接口
        map.remove("appSecret");
        map.remove("date");
        //String end = HttpClientUtil.doPost("http://test.carfaxinfo.com:8090/purchaseReport/order", map);
        System.out.println("234567"+JSON.toJSONString(map));
        HttpResponse execute = HttpRequest.post("http://test.carfaxinfo.com:8090/purchaseReport/order").body(JSON.toJSONString(map)).execute();
        log.info("出险信息详版下单结果===={}",execute.body());
        JSONObject result = JSONObject.parseObject(execute.body());
        String message = result.getString("message");
        if("SUCCESS".equals(message)){
            message = "下单成功";
        }
        Integer code = result.getInteger("code");
        String orderNo = "";
        if(code==0){
            orderNo = result.getJSONObject("data").getString("orderNo");
            code=3004;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encryptType","false");
        jsonObject.put("sign",sn);
        HashMap<String, Object> encrypt = new HashMap<>();
        encrypt.put("reqTime",System.currentTimeMillis());
        encrypt.put("gid",orderNo);
        encrypt.put("data","null");
        encrypt.put("msg",message);
        encrypt.put("code",code);
        encrypt.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        encrypt.put("charge","false");
        encrypt.put("productCode","accurate");
        encrypt.put("version","WAGU001");
        encrypt.put("state","null");
        jsonObject.put("encrypt",JSON.toJSONString(encrypt));
        WbQueryLog wbQueryLog = new WbQueryLog();
        wbQueryLog.setVin(request.getVin());
        wbQueryLog.setProductId(customerProduct.getProductId());
        wbQueryLog.setProductName("出险信息详版2");
        wbQueryLog.setOrderId(orderNo);
        wbQueryLog.setCallBackUrl(request.getCallbackUrl());
        wbQueryLog.setCustomerId(customerInfo.getCustomerId());
        wbQueryLog.setCustomerName(customerInfo.getCustomerName());
        wbQueryLog.setToll("否");
        wbQueryLog.setResult(jsonObject.toJSONString());
        wbQueryLog.setCreateTime(LocalDateTime.now());
        wbQueryLogMapper.insert(wbQueryLog);
        return jsonObject;
    }

    @Override
    public JSONObject queryBackData2(JSONObject json2) {
        JSONObject orderReportVehicle = json2.getJSONObject("orderReportVehicle");
        JSONObject orderReportVehicleExactInsurance = json2.getJSONObject("orderReportVehicleExactInsurance");
        JSONObject data = orderReportVehicleExactInsurance.getJSONArray("data").getJSONObject(0);

        Integer sign = orderReportVehicleExactInsurance.getInteger("sign");
        String gid = orderReportVehicle.getString("orderNo");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("sign",sign);
        stringObjectHashMap.put("encryptType","false");
        HashMap<String, Object> encrypt = new HashMap<>();
        encrypt.put("reqTime",System.currentTimeMillis());
        encrypt.put("gid",gid);
        encrypt.put("userOrderId","");
        encrypt.put("msg","成功");
        encrypt.put("code","0");
        encrypt.put("customerId","e4775b980f5fa7f5f45d291742870cd4");
        encrypt.put("charge",sign==1?"ture":"false");
        encrypt.put("productCode","accurate");
        encrypt.put("version","WAGU001");
        encrypt.put("state","null");
        //resBodyList
        HashMap<String, Object> resBodyList = new HashMap<>();
        //basepart 基础信息
        HashMap<String, Object> basepart = new HashMap<>();
        basepart.put("vin",orderReportVehicle.getString("vin"));
        basepart.put("vehlicno","");
        basepart.put("vehlicnotypecode","");
        basepart.put("querysequenceNo","");
        basepart.put("ressequenceno","");
        basepart.put("createtime",System.currentTimeMillis());
        resBodyList.put("basepart",basepart);
        //vehicleinfo 车辆信息
        HashMap<String, Object> vehicleinfo = new HashMap<>();
        JSONObject clxx = data.getJSONObject("clxx");
        vehicleinfo.put("bodycolor",clxx.getString("csys"));
        vehicleinfo.put("carcategory",clxx.getString("cxlb"));
        vehicleinfo.put("drivernumber",clxx.getString("czrs"));
        vehicleinfo.put("emissionstandards",clxx.getString("pfbz"));
        vehicleinfo.put("firstregisterdate",clxx.getString("ccdjrq"));
        vehicleinfo.put("isbusinessrecord",clxx.getString("sfyyyjl"));
        vehicleinfo.put("lfdate",clxx.getString("scrq"));
        vehicleinfo.put("ownertype",clxx.getString("dqczlx"));
        vehicleinfo.put("usenaturecode",clxx.getString("dqsyxz"));
        vehicleinfo.put("vehicleengineno",clxx.getString("fdjh"));
        vehicleinfo.put("classattribute",clxx.getString("lbsx"));
        vehicleinfo.put("replacementvalue",clxx.getString("xcgzj"));
        vehicleinfo.put("brandname",clxx.getString("ppmc"));
        vehicleinfo.put("motortypecode",clxx.getString("clzl"));
        vehicleinfo.put("vehicledisplacement",clxx.getString("pl"));
        vehicleinfo.put("vehiclestyle",clxx.getString("kxmc"));
        vehicleinfo.put("ratedpasscapa",clxx.getString("hdzkrs"));
        vehicleinfo.put("isnotown",clxx.getString("sfyfgrczqk"));
        resBodyList.put("vehicleinfo",vehicleinfo);

        //underwritinginfo 保险信息
        HashMap<String, Object> underwritinginfo = new HashMap<>();
        JSONObject bxxx = data.getJSONObject("bxxx");
        underwritinginfo.put("isburned",bxxx.getString("sfyghs"));
        underwritinginfo.put("iseffectiveCMI",bxxx.getString("dqsyxyxx"));
        underwritinginfo.put("iseffectiveCPI",bxxx.getString("jqxlxx"));
        underwritinginfo.put("isrobber",bxxx.getString("sfygdq"));
        underwritinginfo.put("iswaterflooded",bxxx.getString("sfygsy"));
        underwritinginfo.put("isseriescoverCDR",bxxx.getString("csxlxx"));
        underwritinginfo.put("isseriescoverCPI",bxxx.getString("jqxlxx"));
        underwritinginfo.put("iseffectiveTPL",bxxx.getString("dqdszzrxyxx"));
        underwritinginfo.put("iseffectiveMVL",bxxx.getString("dqjdcssxyxx"));
        underwritinginfo.put("iscoverageMVL",bxxx.getString("dqcsxbe"));
        underwritinginfo.put("cpidangtimes",bxxx.getString("jqxcxcs"));
        underwritinginfo.put("cmidangtimes",bxxx.getString("syxcxcs"));
        underwritinginfo.put("largeamount",bxxx.getString("zdssje"));
        underwritinginfo.put("largeclaims",bxxx.getString("sfdelp"));
        underwritinginfo.put("recordicpending",bxxx.getString("sfbxajwjajl"));
        underwritinginfo.put("recordIwriteoff",bxxx.getString("sfbxajzxjl"));
        underwritinginfo.put("inscompensation",bxxx.getString("sfbxajjpjl"));
        resBodyList.put("underwritinginfo",underwritinginfo);

        //字符串替换
        JSONArray claim = data.getJSONArray("claim");
        String s1 = claim.toJSONString().replace("sgyy","claimstatus").replace("cxsj","lossoccurdate").replace("ssbw","losspart")
                .replace("clssje","vehiclelossamount").replace("ajzt","optioncause")
                        .replace("sgzrhf","accidentliab").replace("sfdq","isrobber").replace("sfhs","ishotsincedetonation")
                .replace("sfsy","iswaterflooded");
        List<DangerCVO> dangerCVOS = JSON.parseArray(s1, DangerCVO.class);
        Map<String, List<DangerCVO>> collect = dangerCVOS.stream().collect(Collectors.groupingBy(DangerCVO::getCxzl));
        //caclaimdetailslist 商业险出险
        resBodyList.put("caclaimdetailslist",JSON.toJSONString(collect.get("商业险")));
        //ciclaimdetailslist 交强险出险
        resBodyList.put("ciclaimdetailslist",JSON.toJSONString(collect.get("交强险")));
        encrypt.put("data",resBodyList);
        stringObjectHashMap.put("encrypt",JSON.toJSONString(encrypt));
        String json = JSON.toJSONString(stringObjectHashMap);
        System.out.println("================"+json);
        QueryWrapper<WbQueryLog> wbQueryLogQueryWrapper = new QueryWrapper<>();
        wbQueryLogQueryWrapper.eq("order_id",gid);
        WbQueryLog wbQueryLog = wbQueryLogMapper.selectOne(wbQueryLogQueryWrapper);
        String callBackUrl = wbQueryLog.getCallBackUrl();
        Map map = new HashMap<String ,Object>();
        map.put("data",json);
        String s = HttpClientUtil.doPost(callBackUrl, map);
        System.out.println("出险信息详版回调返回数据：========" +s);
        //保存结果
        UpdateWrapper<WbQueryLog> wrapper = new UpdateWrapper<>();
        wrapper.set("result", json);
        wrapper.set("back_time",LocalDateTime.now());
        wrapper.eq("order_id", gid);
        wbQueryLogService.update(wrapper);
        //查询成功扣费
        if(sign.equals("1")){
            UpdateWrapper<WbQueryLog> wrapper2 = new UpdateWrapper<>();
            wrapper2.set("toll", "是");
            wrapper2.set("back_time",LocalDateTime.now());
            wrapper2.eq("order_id", gid);
            wbQueryLogService.update(wrapper2);
            deduction(wbQueryLog.getCustomerId(),productCode.getChuXianCCode());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",Integer.valueOf(JSONObject.parseObject(s).getString("code")));
        jsonObject.put("message","SUCCESS");
        return JSONObject.parseObject(json);
    }

}
