package com.zxb.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zxb.course.dto.CourseDTO;
import com.zxb.course.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-30 14:08
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Reference(interfaceClass = CourseService.class, check = false)
    private CourseService courseService;

    @GetMapping("/courseList")
    public List<CourseDTO> courseList() {
        return courseService.courseList();
    }

}
