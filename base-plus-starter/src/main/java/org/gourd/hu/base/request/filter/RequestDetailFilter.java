/*
 * Copyright 2019-2029 gourd.hu(https://github.com/gourd.hu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gourd.hu.base.request.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.request.bean.RequestDetail;
import org.gourd.hu.base.request.holder.RequestDetailThreadLocal;
import org.gourd.hu.base.request.wrapper.GourdRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 请求详情信息Filter
 *
 * @author gourd.hu
 * @date 2020/3/25
 **/
@Slf4j
@Component("requestDetailFilter")
public class RequestDetailFilter implements Filter {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestDetailFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        GourdRequestWrapper requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            // 设置请求详情信息
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            // 请求IP
            String ip = this.getIpAddr(httpServletRequest);
            // 请求路径
            String path = httpServletRequest.getRequestURI();
            RequestDetail requestDetail = new RequestDetail()
                    .setIp(ip)
                    .setPath(path);
            // 设置请求详情信息
            RequestDetailThreadLocal.setRequestDetail(requestDetail);
            // 判断是否是上传文件接口，如果是则不进行封装
            String contentType = httpServletRequest.getContentType();
            if(StringUtils.isNotBlank(contentType) && contentType.contains(MULTIPART_FORM_DATA)){
                chain.doFilter(request, response);
                return;
            }
            requestWrapper = new GourdRequestWrapper(httpServletRequest);
        }
        // 在chain.doFiler方法中传递新的request对象
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
        log.info("RequestDetailFilter destroy");
        // 释放
        if(RequestDetailThreadLocal.getRequestDetail()!=null){
            RequestDetailThreadLocal.remove();
        }
    }

    private static final String UNKNOWN = "unknown";
    private static final String IPV6_LOCAL = "0:0:0:0:0:0:0:1";

    /**
     * 获取IP地址
     * @Author  gourd.hu
     * @param request
     * @return  String
     *
     */
    private String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (IPV6_LOCAL.equals(ip)){
            ip = getLocalhostIp();
        }
        return ip;
    }
    /**
     * 获取本地ip地址
     *
     * @return
     */
    private String getLocalhostIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取ip异常：",e);
        }
        return null;
    }
}
