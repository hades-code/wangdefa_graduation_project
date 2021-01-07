package org.lhq.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author hades
 */
@Data
@Entity
public class Permission {
	@Id
	@GeneratedValue
	private Integer id;
	private String permissionName;
	private String permissionCode;
}
