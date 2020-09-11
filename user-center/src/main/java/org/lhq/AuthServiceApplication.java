package org.lhq;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: wangdefa_graduation_project
 * @description: Auth
 * @author: Wang defa
 * @create: 2020-09-08 18:05
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.lhq.mapper")
public class AuthServiceApplication {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceApplication.class);
  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(AuthServiceApplication.class, args);
    String info = applicationContext.getEnvironment().getProperty("config.info");
    LOGGER.info("获取到参数:{}",info);
  }
}
