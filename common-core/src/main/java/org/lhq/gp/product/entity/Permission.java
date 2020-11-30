package org.lhq.gp.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hades
 */
@Data
@AllArgsConstructor
public class Permission {
	private Integer id;
	private String permissionName;
	private String permissionCode;
}
