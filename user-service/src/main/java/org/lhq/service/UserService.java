package org.lhq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lhq.entity.User;
import org.lhq.exception.ProjectException;


/**
 * (User)表服务接口
 *
 * @author wangdefa
 * @since 2020-09-15 20:28:37
 */
public interface UserService extends IService<User> {
    /**
     * 登录功能
     * @param
     * @return
     */
    User login(String username,String password);

    /**
     * 注册功能
     * @param user
     * @return
     */
    User register(User user,String code) throws ProjectException;
    void resetPassword(Long userId,String newPassword);
    void activateAccount(Long userId);
    void mailVerificationCode(String mail);
    void changePassword(String oldPassword,String newPassword,Long userId) throws ProjectException;
    void updateStorage(Long userId,Double size,String type);
}
