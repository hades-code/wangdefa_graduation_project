package org.lhq.gp.product.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author hades
 */
@Data
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
