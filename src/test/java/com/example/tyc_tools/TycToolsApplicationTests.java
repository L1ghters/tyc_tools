package com.example.tyc_tools;

import com.example.tyc_tools.model.TycData;
import com.example.tyc_tools.service.init.TycService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class TycToolsApplicationTests {
    @Autowired
    TycService tycService;

    @Test
    void contextLoads() {
    }
    @Test
    public void testReq(){
        //配置请求接口信息
        Map<String, String> param = new HashMap<>();
        param.put("keyword", "北京百度网讯科技有限公司");
        param.put("pageNum", "1");
        param.put("pageSize", "20");
        TycData tycData = tycService.setReqTycApi(param);
        Set<String> propSet = new HashSet<>();
        propSet.add("name");
        propSet.add("type");
        List<Map<String, Object>> choiceDataList = tycService.getChoiceDataList(propSet, tycData);
        System.out.println(choiceDataList);
    }

}
