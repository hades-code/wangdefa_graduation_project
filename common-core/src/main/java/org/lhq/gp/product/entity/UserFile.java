package org.lhq.gp.product.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author hades
 */
@Data
@Entity
public class UserFile {
	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String fileName;
	private String filePath;
	private Long directoryId;
	private String md5;
	private String sha1;
	private String fileType;
	private Long userId;
	private String fileStatus;
	private Double fileSize;
	private Date createTime;
	private Date modifyTime;

}
