package org.lhq.gp.service;

import org.lhq.gp.source.MySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * @program: wangdefa_graduation_project
 * @description: MyService
 * @author: Wang defa
 * @create: 2020-11-02 10:38
 */

@EnableBinding(MySource.class)
public class MySendServcie {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySendServcie.class);
    @Autowired
    private MySource mySource;

    public void sendMsg(String msg){
        LOGGER.info("发送消息:{}",msg);
        mySource.myOutput().send(MessageBuilder.withPayload(msg).build());
    }
}
