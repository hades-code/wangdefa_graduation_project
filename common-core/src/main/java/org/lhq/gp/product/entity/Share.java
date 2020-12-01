package org.lhq.gp.product.entity;

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
	private Long id;
	private Long userFileId;
	private Long userId;
	private String code;
	private String status;
	private Date createTime;
	private Date timeToLive;


}
