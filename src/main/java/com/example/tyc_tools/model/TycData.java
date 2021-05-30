package com.example.tyc_tools.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2020/9/25
 **/
@Data
@ApiModel("天眼查接口返回结果存储类")
@AllArgsConstructor
@NoArgsConstructor
public class TycData implements Serializable {
    @ApiModelProperty("请求参数")
    private Map<String,String> param;
    @ApiModelProperty("接口id")
    private Integer apiId;
    @ApiModelProperty("数据总量")
    private Integer total;
    @ApiModelProperty("数据")
    private List<TycDataSplit> data;
    @ApiModelProperty("数据类型，1分页，2不分页")
    private Integer dataType;
    @ApiModelProperty("报文")
    private String msg;
}
