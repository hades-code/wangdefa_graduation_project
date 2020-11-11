package org.gourd.hu.demo.controller;

import com.baomidou.kaptcha.Kaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.base.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 图形验证码控制器
 * @author gourd.hu
 */
@RestController
@RequestMapping("/kaptcha")
@Api(tags = "图形验证码测试API", description = "图形验证码测试API" )
public class KaptchaController {

  @Autowired
  private Kaptcha kaptcha;

  @GetMapping("/render")
  @ApiOperation(value = "生成验证码")
  public BaseResponse render() {
    kaptcha.render();
    return BaseResponse.ok("生成验证码成功");
  }

  @PostMapping("/valid")
  @ApiOperation(value = "校验验证码")
  public BaseResponse validDefaultTime(@RequestParam String code) {
    // 默认过期时间900秒
    boolean validate = kaptcha.validate(code);
    if(!validate){
      return BaseResponse.fail("验证码校验失败");
    }
    return BaseResponse.ok("验证码校验成功");
  }

  @PostMapping("/validTime")
  @ApiOperation(value = "校验验证码")
  public BaseResponse validWithTime(@RequestParam String code,@RequestParam Long times) {
    boolean validate =kaptcha.validate(code, times);
    if(!validate){
      return BaseResponse.fail("验证码校验失败");
    }
    return BaseResponse.ok("验证码校验成功");
  }

}