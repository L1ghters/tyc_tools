package com.example.tyc_tools.service.init;

import com.example.tyc_tools.model.TycData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dy on 2021/5/23
 **/
public interface TycService {
    TycData setReqTycApi(Map<String, String> param);

    List<Map<String,Object>> getChoiceDataList(Set<String> propSet, TycData tycData);
}
