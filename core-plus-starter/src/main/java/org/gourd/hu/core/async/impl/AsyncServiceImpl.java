package org.gourd.hu.core.async.impl;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.core.async.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * @author gourd.hu
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public Future<BaseResponse> doTaskOne() {
        log.info("开始做任务一（睡眠2s）");
        methodThread();
        HttpServletRequest request = RequestHolder.getRequest();
        log.info("任务一完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
        return new AsyncResult<>(BaseResponse.ok("任务一完成"));
    }

    @Override
    @Async
    public Future<BaseResponse> doTaskTwo(){
        log.info("开始做任务二（睡眠2s）");
        methodThread();
        HttpServletRequest request = RequestHolder.getRequest();
        log.info("任务二完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
        return new AsyncResult<>(BaseResponse.ok("任务二完成"));
    }

    @Override
    @Async
    public void doTaskThree()  {
        log.info("开始做任务三（睡眠1s）");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.interrupted();
            log.error("sleep异常：{}", e);
        }
        HttpServletRequest request = RequestHolder.getRequest();
        log.info("任务三完成，当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
    }


    /**
     * 接口睡眠
     */
    private void methodThread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.interrupted();
            log.error("sleep异常：{}", e);
        }
    }
}
