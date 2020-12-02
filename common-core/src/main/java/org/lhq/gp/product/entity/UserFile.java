package org.lhq.gp.product.entity;

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
	private Long id;
	private String fileName;
	private String filePath;
	private Long directoryId;
	private String md5;
	private String sha1;
	private String fileType;
	private Long userId;
	private String fileStatus;
	private Date createTime;
	private Date modifyTime;

}
