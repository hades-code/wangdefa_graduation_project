package org.lhq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @program: admin-service
 * @description: 管理员服务
 * @author: Wang defa
 * @create: 2021-01-05 11:28
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class,args);
    }
}
