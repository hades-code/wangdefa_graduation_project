package org.gourd.hu.core.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.gourd.hu.core.enums.SensitiveTypeEnum;
import org.gourd.hu.core.serializer.SensitiveInfoSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脱敏注解类
 *
 * @author gourd.hu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
public @interface SensitiveInfo {
 
  SensitiveTypeEnum value();
}
