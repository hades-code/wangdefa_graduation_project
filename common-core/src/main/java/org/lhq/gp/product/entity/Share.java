package org.lhq.gp.product.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Share {

	@Id
	@GeneratedValue
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 分享链接
	 */
	private String shareLink;
	/**
	 * 是否需要提取码
	 */
	private Boolean needCode;
	/**
	 * 分享用户
	 */
	private Long userId;
	/**
	 * 提取码
	 */
	private String shareCode;
	/**
	 * 是否多文件
	 */
	private Boolean multiFile;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 过期时间
	 */
	private Date timeToLive;


}
