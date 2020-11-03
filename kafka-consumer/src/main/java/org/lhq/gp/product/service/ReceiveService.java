package org.lhq.gp.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @program: wangdefa_graduation_project
 * @description: 消息队列
 * @author: Wang defa
 * @create: 2020-10-21 16:47
 */

@EnableBinding(Sink.class)
public class ReceiveService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveService.class);
    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        logger.info("接收到消息{}",payload);
        System.out.println(payload);
    }
}
