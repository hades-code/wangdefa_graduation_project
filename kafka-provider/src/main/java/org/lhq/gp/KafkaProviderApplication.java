package org.lhq.gp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: wangdefa_graduation_project
 * @description: kafka生产者
 * @author: Wang defa
 * @create: 2020-10-21 10:47
 */

@SpringBootApplication
public class KafkaProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProviderApplication.class,args);
    }
}
