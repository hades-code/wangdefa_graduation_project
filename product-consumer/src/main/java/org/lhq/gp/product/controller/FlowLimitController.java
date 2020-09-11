package org.lhq.gp.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-09-10 15:56
 */
@RestController
public class FlowLimitController {
  @GetMapping("/testA")
  public String testA() {
    return "----testA";
  }

  @GetMapping("/testB")
  public String testB() {
    return "----testB";
  }
}
