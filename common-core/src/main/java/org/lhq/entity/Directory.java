package org.lhq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author hades
 */
@Data
@Entity
@Accessors(chain = true)
@ApiModel("目录")
public class Directory {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 目录名称
	 */
	@ApiModelProperty("文件夹名称")
	private String directoryName;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("父目录id")
	private Long parentId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	private LocalDateTime createTime;
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private LocalDateTime modifyTime;
}
