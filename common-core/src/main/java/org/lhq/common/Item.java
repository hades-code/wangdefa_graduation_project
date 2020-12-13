package org.lhq.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author Wallace
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Item {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String type;
	private String name;
	private Double size;
	private String modifyTime;
}
