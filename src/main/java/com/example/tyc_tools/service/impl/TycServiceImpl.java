package com.example.tyc_tools.service.impl;

import com.example.tyc_tools.model.ApiInfo;
import com.example.tyc_tools.model.TycData;
import com.example.tyc_tools.model.TycDataSplit;
import com.example.tyc_tools.service.init.TycService;
import com.example.tyc_tools.utils.TycApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dy on 2021/5/23
 **/
@Service
public class TycServiceImpl implements TycService {
    @Autowired
    TycApiUtils tycApiUtils;

    /**
     * 通过参数列表请求天眼查接口
     * @param param 天眼查请求参数
     * @return
     */
    @Override
    public TycData setReqTycApi(Map<String, String> param) {
        //配置请求接口信息
        ApiInfo apiInfo = new ApiInfo();
        //可传入完整的请求地址，也可传入service后的部分
        //apiInfo.setUrl("http://open.api.tianyancha.com/services/open/ic/humanholding/2.0");
        apiInfo.setSufUrl("/open/ic/humanholding/2.0");
        apiInfo.setApiId(945);
        apiInfo.setParam(param);
        //选填项
        apiInfo.setDataId("id");
        apiInfo.setDataTime("publishDate");
        return tycApiUtils.getTycApiResData(apiInfo);
    }

    /**
     * 筛选返回值的属性
     * @param propSet 选择返回值的属性，可以使用baseInfo.name的方式请求Map类型属性中的属性
     * @param tycData 接口返回值封装类
     * @return
     */
    @Override
    public List<Map<String,Object>> getChoiceDataList(Set<String> propSet, TycData tycData){
        List<Map<String,Object>> result = new ArrayList<>();
        List<TycDataSplit> splitDataList = tycData.getData();
        splitDataList.forEach(tycDataSplit -> {
            Map<String,Object> resultMap = new HashMap<>();
            Map<String, Object> data = tycDataSplit.getData();
            propSet.forEach(s -> {
                Object propData = tycApiUtils.getPropData(s, data);
                resultMap.put(s,propData);
            });
            result.add(resultMap);
        });
        return result;
    }
}
