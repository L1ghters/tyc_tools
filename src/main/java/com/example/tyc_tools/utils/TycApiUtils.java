package com.example.tyc_tools.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.tyc_tools.model.ApiInfo;
import com.example.tyc_tools.model.TycData;
import com.example.tyc_tools.model.TycDataSplit;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2020/9/27
 * 天眼查接口封装工具类
 **/
@Component
public class TycApiUtils {
    private static Logger logger = LoggerFactory.getLogger(TycApiUtils.class);

    @Value("${tyc.token}")
    String token;

    public TycData getTycApiResData(ApiInfo apiInfo) {
        TycData tycData = new TycData();
        String url = apiInfo.getUrl();
        Integer apiId = apiInfo.getApiId();
        tycData.setApiId(apiId);
        Map<String, String> param = apiInfo.getParam();
        tycData.setParam(param);
        StringBuilder sbParam = new StringBuilder();
        param.forEach((key, value) -> {
            sbParam.append(key).append("=").append(value).append("&");
        });
        String finalParam = sbParam.toString();
        finalParam = finalParam.substring(0, finalParam.lastIndexOf("&"));
        String responseStr = getMessageByUrlToken(url, finalParam);
        Map<String, Object> response = (Map<String, Object>) JSONObject.parse(responseStr);
        //获取接口返回的报文
        String msg = getReqMsg(response);
        tycData.setMsg(msg);
        if (response.containsKey("result") && response.get("result") != null){
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            List<TycDataSplit> tycDataSplitList = new ArrayList<>();
            if (result.containsKey("total") && result.get("total") != null && result.containsKey("items")) {
                Integer total = Integer.valueOf(result.get("total").toString());
                tycData.setTotal(total);
                tycData.setDataType(1);
                if (total == 0){
                    return tycData;
                }
                String dataId = apiInfo.getDataId();
                String dataTime = apiInfo.getDataTime();
                List<Map<String,Object>> items = (List<Map<String, Object>>) result.get("items");
                items.forEach(map -> {
                    TycDataSplit tycDataSplit = new TycDataSplit();
                    tycDataSplit.setData(map);
                    if (map.get(dataId) != null && !map.get(dataId).equals("")) {
                        tycDataSplit.setDataId(Long.valueOf(map.get(dataId).toString()));
                    }
                    if (map.get(dataTime) != null && !map.get(dataTime).equals("")) {
                        tycDataSplit.setDataTime(Long.valueOf(map.get(dataTime).toString()));
                    }
                    tycDataSplitList.add(tycDataSplit);
                });
            }else {
                tycData.setTotal(1);
                tycData.setDataType(2);
                TycDataSplit tycDataSplit = new TycDataSplit();
                tycDataSplit.setData(result);
                tycDataSplitList.add(tycDataSplit);
            }
            tycData.setData(tycDataSplitList);
        }else {
            return tycData;
        }
        return tycData;
    }

    //天眼查接口http请求底层方法
    public String getMessageByUrlToken(String path, String param) {
        param = removeParamBlock(param);
        String result = "";
        try {
            String url = path + "?" + param;
            logger.info("tyc.url ==> {}", url);
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            //这里发送get请求
            // 获取当前客户端对象
            request.setHeader("Authorization", token);
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            logger.debug("tyc.result <== {}", result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("tyc.exception <== {}", e.getMessage());
        }
        return result;
    }

    private String removeParamBlock(String param) {
        if (param.contains(" ")){
            param = param.replaceAll(" ", "%20");
        }
        return param;
    }

    public String getReqMsg(Map<String, Object> response) {
        String errorCode = response.get("error_code").toString();
        switch (errorCode) {
            case "0":
                return "请求成功";
            case "300000":
                return "无数据";
            case "300001":
                return "请求失败";
            case "300002":
                return "账号失效";
            case "300003":
                return "账号过期";
            case "300004":
                return "访问频率过快";
            case "300005":
                return "无权限访问此api";
            case "300006":
                return "余额不足";
            case "300007":
                return "剩余次数不足";
            case "300008":
                return "缺少必要参数";
            case "300009":
                return "账号信息有误";
            case "300010":
                return "URL不存在";
            case "300011":
                return "此IP无权限访问此api";
            case "300012":
                return "报告生成中";
            default:
                return null;
        }
    }

    public Object getPropData(String s, Map<String, Object> data) {
        if (s.contains(".")){
            String preProp = s.substring(0, s.indexOf("."));
            String sufProp = s.substring(s.indexOf(".") + 1);
            Map<String,Object> propData = (Map<String, Object>) data.get(preProp);
            return getPropData(sufProp,propData);
        }else {
            return data.get(s);
        }
    }
}
