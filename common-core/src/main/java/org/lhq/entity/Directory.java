package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

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
	private Long id;
	/**
	 * 目录名称
	 */
	@ApiModelProperty("文件夹名称")
	private String directoryName;

	@ApiModelProperty("父目录id")
	@Column(columnDefinition="bigint default 0")
	private Long parentId;

	private Long userId;

	private LocalDateTime createTime;

	private LocalDateTime modifyTime;
}
