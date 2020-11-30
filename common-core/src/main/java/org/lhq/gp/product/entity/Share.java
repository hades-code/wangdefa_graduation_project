package org.lhq.gp.product.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Share {
	@Id
	private Long id;
	private Long userFileId;
	private Long userId;
	private String code;
	private String status;
	private Date createTime;
	private Date timeToLive;
}
