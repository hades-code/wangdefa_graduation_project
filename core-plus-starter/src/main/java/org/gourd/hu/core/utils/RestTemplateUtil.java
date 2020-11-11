package org.gourd.hu.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * restTemplate远程调用工具
 * @author gourd.hu
 */
@Component
public class RestTemplateUtil<T> {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 以get方式请求第三方http接口 getForEntity
     * @param url
     * @return
     */
    public T getForEntity(String url,Class tClass){
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url,tClass);
        return responseEntity.getBody();
    }

    /**
     * 以get方式请求第三方http接口 getForObject
     * 返回值返回的是响应体，省去了我们再去getBody()
     * @param url
     * @return
     */
    public T getForObject(String url,Class tClass){
        return (T)restTemplate.getForObject(url, tClass);
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public <E> T postForEntity(String url,E e,Class tClass){
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, e, tClass);
        return responseEntity.getBody();
    }

    /**
     * 以post方式请求第三方http接口 postForEntity
     * @param url
     * @return
     */
    public <E> T postForObject(String url,E e,Class tClass){
        return (T)restTemplate.postForObject(url, e, tClass);
    }
}