package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.core.async.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @Description 异步测试
 * @Author gourd
 * @Date 2020/4/8 11:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/test")
@Api(tags = "异步测试API", description = "异步测试API" )
@Slf4j
public class AsyncController {
    @Autowired
    private AsyncService asyncService;

    /**
     * 直接使用异步线程池
     */
    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    @GetMapping(value = "/async-task" )
    @ApiOperation(value = "异步任务测试", notes = "异步任务测试")
    public BaseResponse taskExecute(){
        long startTime = System.currentTimeMillis();
        try {
            Future<BaseResponse> r1 = asyncService.doTaskOne();
            Future<BaseResponse> r2 = asyncService.doTaskTwo();
            asyncService.doTaskThree();
            // 异步线程池执行
            asyncTaskExecutor.execute(() -> log.info("^o^============异步线程池执行...."));
            Future<BaseResponse> futureSub = asyncTaskExecutor.submit(() -> { return BaseResponse.ok("异步线程池执行返回结果成功...");});
            while (true) {
                if (r1.isDone() && r2.isDone() && futureSub.isDone()) {
                    log.info("异步任务一、二、线程池提交已完成");
                    break;
                }
            }
            BaseResponse baseResponse1 = r1.get();
            BaseResponse baseResponse2 = r2.get();
            log.info("返回结果：{}，{}",baseResponse1 , baseResponse2);
        } catch (Exception e) {
            log.error("执行异步任务异常 {}",e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        log.info("异步任务总耗时:{}",endTime-startTime);
        return BaseResponse.ok("异步任务全部执行成功");
    }
}
