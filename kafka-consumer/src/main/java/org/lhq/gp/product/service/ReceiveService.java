package org.lhq.gp.product.service;

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
    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        System.out.println(payload);
    }
}
