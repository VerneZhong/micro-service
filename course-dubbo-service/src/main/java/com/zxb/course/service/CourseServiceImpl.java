package com.zxb.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zxb.course.dto.CourseDTO;
import com.zxb.course.entity.Course;
import com.zxb.course.repository.CourseRepository;
import com.zxb.course.thrift.ServiceProvider;
import com.zxb.thrift.user.UserInfo;
import com.zxb.thrift.user.UserService;
import com.zxb.thrift.user.dto.TeacherDTO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-29 17:53
 */
@Service(interfaceClass = CourseService.class)
@Component
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courseDTOS = new ArrayList<>();
        List<Course> courseList = courseRepository.findAll();
        UserService.Client userService = serviceProvider.getUserService();
        for (Course course : courseList) {
            try {
                CourseDTO courseDTO = new CourseDTO();
                BeanUtils.copyProperties(course, courseDTO);
                Integer teacherId = courseRepository.getCourseByTeacher(course.getId());
                UserInfo teacher = userService.getTeacherById(teacherId);

                TeacherDTO teacherDTO = new TeacherDTO();
                BeanUtils.copyProperties(teacher, teacherDTO);
                courseDTO.setTeacher(teacherDTO);
                courseDTOS.add(courseDTO);
            } catch (TException e) {
                e.printStackTrace();
            }
        }
        return courseDTOS;
    }
}
