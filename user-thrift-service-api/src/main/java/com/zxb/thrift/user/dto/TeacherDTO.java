package com.zxb.thrift.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-29 17:36
 */
@Data
public class TeacherDTO extends UserDTO implements Serializable {

    private String intro;
    private Integer stars;
}
