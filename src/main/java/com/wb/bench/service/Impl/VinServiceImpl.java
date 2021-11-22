package com.wb.bench.service.Impl;

import com.alibaba.fastjson.JSON;
import com.wb.bench.request.VinRequest;
import com.wb.bench.service.VinService;
import com.wb.bench.util.HttpClientUtil;
import org.apache.tomcat.jni.SSL;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VinServiceImpl implements VinService {
    final String URL ="https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo";

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
        HttpClientUtil.doPostJson(URL, jsonString,mvTrackId);
       return  HttpClientUtil.doPostJson(URL, jsonString,mvTrackId);
    }

    public static void main(String[] args) throws Exception {
        String mvTrackId ="20170926105632_VehicleInsuranceInfo_zhongpuweixin_sa23jhfu";
        Map map = new HashMap<String ,Object>();
        map.put("loginName","zhongpuweixin");
        map.put("pwd","zhongpuweixin1205");
        map.put("serviceName","VehicleInsuranceInfo");
        Map map1 = new HashMap<String ,String>();
        map1.put("vin","LHGCR1657E8041428");
        map.put("param",map1);
        String jsonString = JSON.toJSONString(map);
        System.out.println(jsonString);
        String s = HttpClientUtil.doPostJson("https://www.miniscores.net:8313/CreditFunc/v2.1/VehicleInsuranceInfo", jsonString,mvTrackId);
        System.out.println(s);
    }
}
