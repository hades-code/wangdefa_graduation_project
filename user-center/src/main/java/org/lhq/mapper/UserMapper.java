package org.lhq.mapper;


import org.lhq.gp.product.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Wallace
 */
public interface UserMapper extends JpaRepository<User, Long> {

}
