package com.example.tyc_tools.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel("单次采集数据类")
@NoArgsConstructor
@AllArgsConstructor
public class TycDataSplit implements Serializable {
    @ApiModelProperty("数据")
    private Map<String,Object> data;
    @ApiModelProperty("数据id")
    private Long dataId;
    @ApiModelProperty("数据发布时间")
    private Long dataTime;
}
