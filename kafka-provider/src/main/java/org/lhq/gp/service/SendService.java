package org.lhq.gp.service;

import org.lhq.gp.source.MySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-10-21 11:01
 */

@EnableBinding(Source.class)
public class SendService {
    private final Logger logger = LoggerFactory.getLogger(SendService.class);
    @Resource
    private Source source;

    public void sendMsg(String msg){
        logger.info("发送消息:{}",msg);
        source.output().send(MessageBuilder.withPayload(msg).build());
    }
}
