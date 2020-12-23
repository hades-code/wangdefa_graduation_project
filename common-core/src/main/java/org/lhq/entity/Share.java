package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Accessors(chain = true)
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
	 * 分享用户
	 */
	private Long userId;
	/**
	 * 提取码
	 */
	private String shareCode;
	/**
	 * 是否需要提取码
	 */
	private Boolean shareLock;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 过期时间
	 */
	private LocalDateTime expirationTime;


}
