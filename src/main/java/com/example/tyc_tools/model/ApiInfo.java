package com.example.tyc_tools.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by dy on 2020/9/23
 **/
@Data
@ApiModel("天眼查接口信息类")
@AllArgsConstructor
@NoArgsConstructor
public class ApiInfo implements Serializable {
    @ApiModelProperty("接口名称")
    private String name;
    @ApiModelProperty("接口id")
    private Integer apiId;
    @ApiModelProperty("请求路径前缀")
    private String preUrl = "http://open.api.tianyancha.com/services";
    @ApiModelProperty("请求路径后缀")
    private String sufUrl;
    @ApiModelProperty("请求路径")
    private String url;
    @ApiModelProperty("请求参数")
    private Map<String,String> param;
    @ApiModelProperty("id类数据字段")
    private String dataId;
    @ApiModelProperty("时间类数据字段")
    private String dataTime;

    public String getUrl() {
        if (url == null && sufUrl != null){
            url = preUrl + sufUrl;
            this.setUrl(url);
        }
        return url;
    }
}
