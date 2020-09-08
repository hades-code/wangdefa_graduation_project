package org.lhq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: wangdefa_graduation_project
 * @description: Auth
 * @author: Wang defa
 * @create: 2020-09-08 18:05
 */

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServiceApplication.class, args);
  }
}
