package org.lhq.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.crypto.digest.DigestUtil;
import org.lhq.entity.User;
import org.lhq.entity.vo.ResultVO;
import org.lhq.exception.ProjectException;
import org.lhq.service.UserService;
import org.lhq.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wallace
 */
@RestController
@RequestMapping("auth")
@Slf4j
@Api(tags = "授权接口")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserService userService;

    @PostMapping("login")
    public Map login(String username, String password, HttpServletResponse response) throws ProjectException {
        User user = new User().setUsername(username).setPassword(password);
        LOGGER.info("登录行动:{}", user);
        if (user == null) {
            throw new ProjectException("登陆用户为空");
        }
        String md5Hex = DigestUtil.md5Hex(password);
        User loginUser = userService.login(username, md5Hex);
        if (loginUser.getUsername() == null || "".equals(loginUser.getUsername())) {
            LOGGER.error("用户名或密码错误");
            throw new ProjectException("用户名或密码错误");
        }
        if (!StrUtil.equals(loginUser.getPassword(), user.getPassword())) {
            LOGGER.error("用户名或密码错误");
            throw new ProjectException("用户名或密码错误");
        }
        else {
            String token = JwtUtil.createJwt(loginUser.getId(), loginUser.getUsername(), "user");
            response.setHeader(JwtUtil.AUTH_HEADER_KEY, JwtUtil.TOKEN_PREFIX + token);
            response.setHeader("Access-Control-Expose-Headers", JwtUtil.AUTH_HEADER_KEY);
            Map<String, Object> resultMap = new HashMap<>(2);
            resultMap.put("user", loginUser);
            resultMap.put("token", JwtUtil.TOKEN_PREFIX + token);
            return resultMap;

        }

    }

    @PostMapping("register")
    public ResponseEntity<ResultVO<User>> register(User user) throws ProjectException {
        LOGGER.info("请求注册方法");
        LOGGER.info("获得的User：{}", user);
        User register = userService.register(user);
        return ResponseEntity.ok(new ResultVO<User>().setMessage("注册成功").setData(register));
    }
    @PostMapping("refresh")
    public String refreshToken(){
        return "newToken";
    }
    @PostMapping("resetPassword")
    public void resetPassword(Long userId){

    }
    @PostMapping("changePassword")
    public void changePassword(Long userId,String oldPassword,String newPassword) throws ProjectException {
        this.userService.changePassword(oldPassword,newPassword,userId);
    }

    @PostMapping("userInfo")
    public Map userInfo(String token) {
        log.info(token);
        HashMap<String, Object> map = new HashMap<>();
        map.put("label", "年轻人不讲武德");
        map.put("location", "年轻人不讲武德");
        map.put("position", "武汉");
        map.put("skill", "闪电五连鞭");
        map.put("username", "test");
        return map;
    }
}
