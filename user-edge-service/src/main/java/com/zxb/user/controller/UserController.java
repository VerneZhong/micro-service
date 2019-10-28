package com.zxb.user.controller;

import com.zxb.message.thrift.user.UserInfo;
import com.zxb.message.thrift.user.UserService;
import com.zxb.message.thrift.user.dto.UserDTO;
import com.zxb.user.config.RedisClient;
import com.zxb.user.exception.LoginException;
import com.zxb.user.request.RegisterUserRequest;
import com.zxb.user.response.Response;
import com.zxb.user.thrift.ServiceProvider;
import com.zxb.user.util.MD5Util;
import com.zxb.user.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.zxb.user.response.Response.MOBILE_OR_EMAIL_REQUIRED;
import static com.zxb.user.response.Response.USERNAME_PASSWORD_INVALID;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-27 16:31
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        // 验证用户名和密码
        try {
            UserInfo userInfo = serviceProvider.getUserService().getUserByName(username);

            if (userInfo == null) {
                throw new LoginException();
            }
            if (!Objects.equals(MD5Util.md5(password), userInfo.getPassword())) {
                throw new LoginException();
            }

            // 生成token
            String token = TokenUtil.getToken();

            // 缓存用户到Redis
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userInfo, userDTO);
            redisClient.set(token, userDTO, 3600);

            return Response.success(token);
        } catch (Exception e) {
            e.printStackTrace();
            return USERNAME_PASSWORD_INVALID;
        }
    }

    @PostMapping("/sendVerifyCode")
    @ResponseBody
    public Response sendVerifyCode(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email) {
        String code = TokenUtil.randomCode("0123456789", 6);
        String message = "验证码：" + code;
        try {
            boolean result;
            if (StringUtils.isNoneBlank(mobile)) {
                result = serviceProvider.getMessageService().sendMobileMessage(mobile, message);
                redisClient.set(mobile, code);
            } else if (StringUtils.isNoneBlank(email)) {
                result = serviceProvider.getMessageService().sendEmailMessage(email, message);
                redisClient.set(email, code);
            } else {
                return MOBILE_OR_EMAIL_REQUIRED;
            }
            if (!result) {
                return Response.SEND_VERIFY_CODE_FAILED;
            }
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.success();
    }

    @PostMapping("/register")
    @ResponseBody
    public Response register(@RequestBody @Valid RegisterUserRequest userRequest) {
        if (StringUtils.isBlank(userRequest.getMobile()) && StringUtils.isBlank(userRequest.getEmail())) {
            return MOBILE_OR_EMAIL_REQUIRED;
        }
        if (StringUtils.isNoneBlank(userRequest.getMobile())) {
            String redisCode = (String) redisClient.get(userRequest.getMobile());
            if (!Objects.equals(userRequest.getVerifyCode(), redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        } else {
            String redisCode = (String) redisClient.get(userRequest.getEmail());
            if (!Objects.equals(userRequest.getVerifyCode(), redisCode)) {
                return Response.VERIFY_CODE_INVALID;
            }
        }
        try {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(userRequest, userInfo);
            userInfo.setPassword(MD5Util.md5(userRequest.getPassword()));
            UserService.Client userService = serviceProvider.getUserService();
            userService.registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.success();
    }

    @PostMapping("/authentication")
    @ResponseBody
    public UserDTO authentication(@RequestHeader("token") String token) {
        return (UserDTO) redisClient.get(token);
    }

}
