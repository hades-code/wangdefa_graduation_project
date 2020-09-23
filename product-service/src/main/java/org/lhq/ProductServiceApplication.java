package org.lhq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: wangdefa_graduation_project
 * @description: 产品应用
 * @author: Wang defa
 * @create: 2020-09-15 15:21
 */

@SpringBootApplication
@MapperScan("org.lhq.dao")
public class ProductServiceApplication {
  public static void main(String[] args) {
      SpringApplication.run(ProductServiceApplication.class,args);
  }
}
