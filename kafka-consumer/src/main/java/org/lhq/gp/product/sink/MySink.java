package org.lhq.gp.product.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-11-02 11:14
 */


public interface MySink {
    String INPUT = "myMq";

    @Input(INPUT)
    SubscribableChannel input();
}
