package org.lhq.gp.product.service;

import org.lhq.gp.product.sink.MySink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import sun.reflect.generics.tree.VoidDescriptor;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-11-02 11:18
 */

@EnableBinding(MySink.class)
public class MyReceiveService {
    private static final Logger logger = LoggerFactory.getLogger(MyReceiveService.class);

    @StreamListener(MySink.INPUT)
    public void receive(Object payload){
        logger.info("接收到消息{}",payload);
        System.out.println(payload);
    }
}
