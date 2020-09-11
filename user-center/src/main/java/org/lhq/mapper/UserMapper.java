package org.lhq.mapper;

import org.lhq.gp.product.entity.User;

/**
 * @author Wallace
 */
public interface UserMapper {
        User selectOneByUser(User user);
        Integer addUser(User user);
        Integer delectUser(User user);
        Integer updateUser(User user);

}
