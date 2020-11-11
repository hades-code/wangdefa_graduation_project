package org.gourd.hu.core.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description id请求
 * @Author gourd.hu
 * @Date 2020/5/18 10:39
 * @Version 1.0
 */
@Data
@ApiModel(value = "id请求对象", description = "id请求对象")
public class BaseIdDTO<T> {

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "id不能为空")
    private T id;
}
