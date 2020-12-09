package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author hades
 */
@Data
@Entity
public class Directory {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 目录名称
	 */
	private String directoryName;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	private Date createTime;
	private Date modifyTime;
}