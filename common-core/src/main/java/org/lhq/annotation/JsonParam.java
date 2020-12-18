package org.lhq.annotation;

import java.lang.annotation.*;

/**
 * @author hades
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {
	/**
	 * 默认用于请求参数的名字
	 * @return
	 */
	String value() default "";

	/**
	 * 是否必须
	 * @return
	 */
	boolean required() default true;

	/**
	 * 默认值
	 * @return
	 */
	String defaultValue() default "";
}
