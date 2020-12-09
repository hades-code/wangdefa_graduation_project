package org.lhq.controller;



import io.swagger.annotations.Api;
import org.lhq.gp.product.common.Result;
import org.lhq.gp.product.common.ResultCode;
import org.lhq.gp.product.entity.User;
import org.lhq.service.UserService;
import org.lhq.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace
 */
@RestController
@RequestMapping("auth")
@Api(tags = "授权接口")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserService userService;

    @PostMapping("login")
    public ResponseEntity<Object> login(String username, String password, HttpServletResponse response){
    	User user = new User().setUsername(username).setPassword(password);
        LOGGER.info("登录行动:{}",user);
        if (user == null){
           return ResponseEntity.ok(new Result<>().setResultCode(ResultCode.FAIL));
        }
        User loginUser = userService.login(user);
        if (loginUser.getUsername() == null|| "".equals(loginUser.getUsername()) ){
            LOGGER.info("用户名或密码错误");
			return ResponseEntity.ok(new Result<>().setResultCode(ResultCode.FAIL).setMessage("用户名或密码错误"));
        }else {
            String token = JwtUtil.createJwt(loginUser.getId(),loginUser.getUsername(),"user");
            response.setHeader(JwtUtil.AUTH_HEADER_KEY,JwtUtil.TOKEN_PREFIX+token);
            response.setHeader("Access-Control-Expose-Headers", JwtUtil.AUTH_HEADER_KEY);
            Map<String, Object> resultMap = new HashMap<>(16);
            resultMap.put("user",loginUser);
            resultMap.put("token",JwtUtil.TOKEN_PREFIX+token);
            return ResponseEntity.ok(new Result<>().setData(resultMap));

        }

    }
    @PostMapping("register")
    public ResponseEntity<Result<User>> register(User user){
        //明天再写
        LOGGER.info("请求注册方法");
        //user.setId(1L);
        LOGGER.info("获得的User：{}",user);
        User register = userService.register(user);
        return ResponseEntity.ok(new Result<User>()
				.setMessage("注册成功")
				.setData(register));
    }
    @GetMapping("article")
    public ResponseEntity article(){
		HashMap<String, Integer> map = new HashMap<>();
		map.put("code",200);
		return ResponseEntity.ok(map);
	}
}
