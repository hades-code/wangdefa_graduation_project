package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class UserFile {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String filePath;
	@JsonSerialize(using = ToStringSerializer.class)
	/**
	 * 文件所在目录
	 */
	private Long directoryId;
	/**
	 * 文件md5值
	 */
	private String md5;
	private String sha1;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件属于的用户
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 文件属于用户
	 */
	private String fileStatus;
	/**
	 * 文件尺寸
	 */
	private Double fileSize;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime modifyTime;

}
