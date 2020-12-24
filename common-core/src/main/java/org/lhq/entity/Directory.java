package org.lhq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author hades
 */
@Data
@Entity
@Accessors(chain = true)
@ApiModel("目录")
@AllArgsConstructor
@RequiredArgsConstructor
public class Directory  {
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
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createTime;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime modifyTime;
}
