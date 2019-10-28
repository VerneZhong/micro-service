package com.zxb.user.repository;

import com.zxb.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-10-26 17:57
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
