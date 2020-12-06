package org.lhq.gp.product.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Item {
	private Long id;
	private String type;
	private String name;
}
