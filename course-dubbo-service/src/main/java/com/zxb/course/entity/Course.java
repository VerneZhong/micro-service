package com.zxb.course.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-29 17:55
 */
@Data
@Entity
@Table(name = "pe_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
}
