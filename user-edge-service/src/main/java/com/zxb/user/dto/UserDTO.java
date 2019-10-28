package com.zxb.user.dto;

import lombok.Data;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-28 10:53
 */
@Data
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String mobile;
    private String email;
}
