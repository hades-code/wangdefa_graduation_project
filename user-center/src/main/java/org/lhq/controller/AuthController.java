package org.lhq.controller;

import org.lhq.gp.product.entity.ResponseEntity;
import org.lhq.gp.product.entity.ResultCode;
import org.lhq.gp.product.entity.User;
import org.lhq.service.UserService;
import org.lhq.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody User user, HttpServletResponse response){
        LOGGER.info("登录行动:{}",user);
        User loginUser = userService.login(user.getUsername(), user.getUsername());
        if (loginUser.getUsername() == null|| "".equals(loginUser.getUsername()) ){
            LOGGER.info("用户名或密码错误");
        }else {
            //TO-DO:
            String token = JwtUtil.createJwt(loginUser.getId(),loginUser.getUsername(),"user");
            response.setHeader(JwtUtil.AUTH_HEADER_KEY,JwtUtil.TOKEN_PREFIX+token);
            response.setHeader("Access-Control-Expose-Headers", JwtUtil.AUTH_HEADER_KEY);
            return new ResponseEntity<User>()
                    .setMessage("登陆成功")
                    .setResultCode(ResultCode.SUCCESS)
                    .setData(loginUser);

        }
        return new ResponseEntity<>()
                .setMessage("用户名或密码错误")
                .setResultCode(ResultCode.FAIL);
    }
    @PostMapping("register")
    public ResponseEntity register(User user){
        //明天再写
        return null;
    }
}
