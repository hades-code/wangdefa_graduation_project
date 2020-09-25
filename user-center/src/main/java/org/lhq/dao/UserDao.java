package org.lhq.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lhq.gp.product.entity.User;


/**
 * @author Wallace
 */
public interface UserDao extends BaseMapper<User> {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User selectByUsername(String username);
}
