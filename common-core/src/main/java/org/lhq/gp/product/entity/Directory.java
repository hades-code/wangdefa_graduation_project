package org.lhq.gp.product.entity;

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
	private Long id;
	/**
	 * 目录名称
	 */
	private String directoryName;
	private Long parentId;
	private Long userId;
	private Date createTime;
	private Date modifyTime;
}
