package com.zxb.user.repository;

import com.zxb.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-10-26 17:57
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "select u.id, u.username, u.password, u.realname, " +
            "u.mobile, u.email, t.intro, t.stars from pe_user u, pe_teacher t " +
            "where u.id = ?1 and u.id = t.user_id", nativeQuery = true)
    Map queryByTeacherById(@Param("id") Integer id);

}
