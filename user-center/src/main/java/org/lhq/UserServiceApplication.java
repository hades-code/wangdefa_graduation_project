package org.lhq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: wangdefa_graduation_project
 * @description: Auth
 * @author: Wang defa
 * @create: 2020-09-08 18:05
 */

@SpringBootApplication
//@EnableDiscoveryClient
@MapperScan("org.lhq.dao")
@EnableCaching
public class UserServiceApplication {
    public static void main(String[] args) {
     SpringApplication.run(UserServiceApplication.class, args);
  }
}
