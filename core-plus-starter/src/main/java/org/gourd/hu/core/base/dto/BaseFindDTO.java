package org.gourd.hu.core.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.gourd.hu.core.enums.SortEnum;


/**
 * 查询分页基础类
 * @author gourd.hu
 */
@Data
@ApiModel(value = "查询分页基础对象", description = "查询分页基础对象")
public class BaseFindDTO {

    @ApiModelProperty("页数，默认值 1")
    private Integer pageNo =1;

    @ApiModelProperty("每页条数，默认值 10")
    private Integer pageSize =10;

    @ApiModelProperty("排序字段")
    private String orderColumn;

    @ApiModelProperty("排序类型")
    private SortEnum orderType;

}
