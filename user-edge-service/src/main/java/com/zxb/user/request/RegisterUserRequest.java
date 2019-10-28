package com.zxb.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-28 13:05
 */
@Data
public class RegisterUserRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
    private String mobile;
    private String email;
    @NotNull
    private String verifyCode;
}
