package org.lhq.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hades
 */
@Data
@Accessors(chain = true)
@ApiModel("接受请求参数")
public class Request {
	@ApiModelProperty("用户id")
	Long userId;
	@ApiModelProperty("接收的数据")
	List<Item> data;
}
