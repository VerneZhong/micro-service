package com.zxb.user.service;


import com.zxb.thrift.user.UserInfo;
import com.zxb.thrift.user.UserService;
import com.zxb.user.entity.User;
import com.zxb.user.repository.UserRepository;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService.Iface userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserById() throws TException {
        UserInfo user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    public void getUserByName() {
    }

    @Test
    public void registerUser() throws TException {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("zxb");
        userInfo.setPassword("123456");
        userInfo.setRealName("钟学斌");
        userInfo.setMobile("13012129999");
        userInfo.setEmail("1231212@qq.com");

        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        userRepository.save(user);
    }
}