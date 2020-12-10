package org.lhq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Wallace
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Item {
	private Long id;
	private String type;
	private String name;
	private String modifyTime;
}
