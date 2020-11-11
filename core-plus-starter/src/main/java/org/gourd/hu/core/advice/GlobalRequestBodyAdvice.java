package org.gourd.hu.core.advice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.core.annotation.RemoveStrEmpty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author gourd.hu
 * @title 全局请求参数处理类
 */
@ControllerAdvice(basePackages = "org.gourd.hu")
@Slf4j
public class GlobalRequestBodyAdvice implements RequestBodyAdvice {


    /**
     * 此处如果返回false , 则不执行当前Advice的业务
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return methodParameter.getMethod().isAnnotationPresent(XXApiReq.class);
        return true;
    }

    /**
     * 读取参数前执行
     * 在此做些编码 / 解密 / 封装参数为对象的操作
     *
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * 读取参数后执行
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        Field[] declaredFields = body.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //打开私有访问
            field.setAccessible(true);
            RemoveStrEmpty removeStrEmpty = field.getAnnotation(RemoveStrEmpty.class);
            if(removeStrEmpty != null && field.getType().equals(String.class)){
                // 去除空字符串
                removeStrEmpty(body, field);
            }
        }
        return body;
    }

    /**
     * 无请求时的处理
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 去除空字符串
     *
     * @param body
     * @param field
     */
    private void removeStrEmpty(Object body, Field field) throws IllegalAccessException {
        //获取属性值
        Object value = null;
        value = field.get(body);
        if (value != null && value.toString().contains(StringUtils.SPACE)) {
            field.set(body, StringUtils.deleteWhitespace(value.toString()));
        }
    }
}

