package org.lhq.gp.product.entity;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author hades
 */
@Data
public class Directory {
	@Id
	private Long id;
	/**
	 * 目录名称
	 */
	private String directoryName;
	private String parentId;
	private String userId;
	private Date createTime;
	private Date modifyTime;
}
