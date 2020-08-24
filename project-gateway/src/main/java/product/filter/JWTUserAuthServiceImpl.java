package product.filter;

import com.github.davidfantasy.jwtshiro.JWTUserAuthService;
import com.github.davidfantasy.jwtshiro.UserInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @program: wangdefa_graduation_project
 * @description:
 * @author: Wang defa
 * @create: 2020-08-24 17:25
 */

@Service
public class JWTUserAuthServiceImpl implements JWTUserAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUserAuthServiceImpl.class);



    private Cache<String, UserInfo> userCache = CacheBuilder.newBuilder().maximumSize(1000)
            .expireAfterWrite(30, TimeUnit.MINUTES).build();

    @Override
    public UserInfo getUserInfo(String account) {
        try {
            UserInfo user = userCache.getIfPresent(account);
            if (user == null) {
                //user = this.queryUserInfo(account);
                if (user != null) {
                    userCache.put(account, user);
                }
            }
            return user;
        } catch (Exception e) {
            LOGGER.error("读取用户缓存信息发生错误:" + e.getMessage());
        }
        return null;
    }

    /**
     * 自定义访问资源认证失败时的处理方式，例如返回json格式的错误信息
     * {\"code\":401,\"message\":\"用户认证失败！\")
     */
    @Override
    public void onAuthenticationFailed(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 自定义访问资源权限不足时的处理方式，例如返回json格式的错误信息
     * {\"code\":403,\"message\":\"permission denied！\")
     */
    @Override
    public void onAuthorizationFailed(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpStatus.FORBIDDEN.value());
    }

    private UserInfo queryUserInfo(String account) {
        // 这里编写获取ShiroUserInfo的逻辑，例如从数据库进行查询
        return null;
    }

    /**
     * 调用接口的getAuthenticatedUser获取当前请求的用户信息
     */
    public UserInfo getCurrentUser(){
        return (UserInfo)this.getAuthenticatedUser(false);
    }

    /**
     * 刷新指定account的缓存信息
     */
    public void refreshUserCache(String account) {
        this.userCache.invalidate(account);
    }

}
