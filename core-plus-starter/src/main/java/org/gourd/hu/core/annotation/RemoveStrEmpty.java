package org.gourd.hu.core.annotation;


import java.lang.annotation.*;


/**
 * 删除空字符串注解
 * @author gourd.hu
 * @date 2018-08-26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoveStrEmpty {


}