package org.gourd.hu.core.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description 批量id请求
 * @Author gourd.hu
 * @Date 2020/5/18 10:39
 * @Version 1.0
 */
@Data
@ApiModel(value = "批量id请求对象", description = "批量id请求对象")
public class BatchIdDTO<T> {

    @ApiModelProperty(value = "id集合",required = true)
    @NotNull(message = "id集合不能为空")
    private List<T> ids;
}
