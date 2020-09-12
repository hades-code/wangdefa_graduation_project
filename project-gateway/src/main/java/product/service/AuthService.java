package product.service;

import org.lhq.gp.product.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
@RequestMapping("auth")
public interface AuthService {
    @PostMapping("login")
    User login(@RequestBody User user);
}
