package com.wb.bench.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wb.bench.request.VinRequest;
import com.wb.bench.service.VinService;
import com.wb.bench.util.HttpClientUtil;
import com.wb.bench.util.MD5Util;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class VinServiceImpl implements VinService {
    final String URL ="https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo";
    final String customerId = "cddd****************087475801189";
    final String QXURL ="entapiceshi.qucent.cn/api";

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
        String s = HttpClientUtil.doPostJson(URL, jsonString, mvTrackId);
        System.out.println(s);
        return  HttpClientUtil.doPostJson(URL, jsonString,mvTrackId);
    }

    @Override
    public String queryVinInfo(VinRequest vinRequest) throws Exception {
        Map map = new LinkedHashMap<String ,Object>();
        map.put("customerId",customerId);
        Map vinMap = new HashMap<String ,String>();
        vinMap.put("vin",vinRequest.getVin());
        map.put("encrypt",JSON.toJSONString(vinMap));
        map.put("encryptType","false");
        map.put("productCode","BA610011");
        map.put("encryptType","false");
        map.put("reqTime", System.currentTimeMillis());
        map.put("version","=V001");
        String sign = (MD5Util.md5Hex(map.toString(), "utf-8"));
        map.put("sign",sign);
        System.out.println(map);
        return null;
    }

    public static void main(String[] args) throws Exception {
//        String mvTrackId ="20170926105632_VehicleInsuranceInfo_zhongpuweixin_sa23jhfu";
//        Map map = new HashMap<String ,Object>();
//        map.put("loginName","zhongpuweixin");
//        map.put("pwd","zhongpuweixin1205");
//        map.put("serviceName","VehicleInsuranceInfo");
//        Map map1 = new HashMap<String ,String>();
//        map1.put("vin","LHGCR1657E8041428");
//        map.put("param",map1);
//        String jsonString = JSON.toJSONString(map);
//        System.out.println(jsonString);
//        String s = HttpClientUtil.doPostJson("https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo", jsonString,mvTrackId);
//        System.out.println(s);

        Map map = new LinkedHashMap<String ,Object>();
        map.put("customerId","customerId");
        Map vinMap = new HashMap<String ,String>();
        vinMap.put("vin","LHGCR1657E8041428");
        map.put("encrypt",JSON.toJSONString(vinMap));
        map.put("encryptType","false");
        map.put("productCode","BA610011");
        map.put("encryptType","false");
        map.put("reqTime", System.currentTimeMillis());
        map.put("version","=V001");
        String sign = (MD5Util.md5Hex(map.toString(), "utf-8"));
        map.put("sign",sign);
        System.out.println(map);
        String s = HttpClientUtil.doPostJson("https://host/v3/entapi.qucent.cn/api", map.toString(),null);
        System.out.println(s);
    }
}
