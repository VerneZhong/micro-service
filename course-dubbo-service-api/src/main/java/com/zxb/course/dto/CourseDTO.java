package com.zxb.course.dto;

import com.zxb.thrift.user.dto.TeacherDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-29 17:30
 */
@Data
public class CourseDTO implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private TeacherDTO teacher;
}
