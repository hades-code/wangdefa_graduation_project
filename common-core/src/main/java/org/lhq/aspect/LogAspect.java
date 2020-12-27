package org.lhq.aspect;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: wangdefa_graduation_project
 * @description: 日志切面类
 * @author: Wang defa
 * @create: 2020-12-19 10:47
 */

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * org.lhq.controller..*.*(..))")
    public void controllerMethod(){
    }

    /**
     * 执行方法前
     * @param joinPoint
	 */
    @Before("controllerMethod()")
    public void LogRequestInfo(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuffer requestLog = new StringBuffer();
        Signature signature = joinPoint.getSignature();

        requestLog.append("请求信息：").append("URL = {").append(request.getRequestURI()).append("},\t")
                .append("请求方式 = {").append(request.getMethod()).append("},\t")
                .append("请求IP = {").append(request.getRemoteAddr()).append("},\t")
                .append("类方法 = {").append(signature.getDeclaringTypeName()).append(".")
                .append(signature.getName()).append("},\t");

        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0 || paramValues == null) {
            requestLog.append("请求参数 = {} ");
        } else {
            requestLog.append("请求参数 = [");
            for (int i = 0; i <= paramLength - 1; i++) {
                requestLog.append(paramNames[i]).append("=").append(paramValues[i]).append(",");
            }
            requestLog.append("]");
        }
        log.info(requestLog.toString());
    }
    @AfterReturning(returning = "object",pointcut = "controllerMethod()")
    public void logResultVOInfo(Object object){
        log.info("请求结果:{}",StrUtil.toString(object));
    }
}
