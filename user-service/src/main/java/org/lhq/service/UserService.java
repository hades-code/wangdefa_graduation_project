package org.lhq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lhq.entity.User;


/**
 * (User)表服务接口
 *
 * @author wangdefa
 * @since 2020-09-15 20:28:37
 */
public interface UserService extends IService<User> {
    /**
     * 登录功能
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 注册功能
     * @param user
     * @return
     */
    User register(User user);
    void updateStorage(Long userId,Double size,String type);
}
