package com.zxb.user.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-26 17:55
 */
@Entity
@Data
@Table(name = "pe_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Column(name = "realname")
    private String realName;
    private String email;
    private String mobile;
}
