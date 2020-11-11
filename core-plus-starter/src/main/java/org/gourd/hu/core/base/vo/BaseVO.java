package org.gourd.hu.core.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 基础vo对象
 * @author gourd.hu
 * @Date 2020/1/9 15:41
 * @Version 1.0
 */
@Data
@ApiModel(value = "基础vo对象", description = "基础vo对象")
public class BaseVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("承租人id")
    private Long tenantId;


}
