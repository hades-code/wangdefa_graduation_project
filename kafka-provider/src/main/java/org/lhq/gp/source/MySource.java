package org.lhq.gp.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {
    String str = "myMq";

    @Output(str)
    MessageChannel myOutput();
}
