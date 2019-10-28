package com.zxb.user.service;

import com.zxb.message.thrift.user.UserInfo;
import com.zxb.message.thrift.user.UserService;
import com.zxb.user.entity.User;
import com.zxb.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-25 23:33
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo getUserById(int id) throws TException {
        Optional<User> userOptional = userRepository.findById(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userOptional.get(), userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        User user = userRepository.findByUsername(username);
        log.info("load db_user data: [{}]", user);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        log.info("register User : {}", userInfo);
        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        userRepository.save(user);
    }
}
