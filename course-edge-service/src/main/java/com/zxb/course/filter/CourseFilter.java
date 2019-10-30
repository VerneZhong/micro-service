package com.zxb.course.filter;

import com.zxb.thrift.user.dto.UserDTO;
import com.zxb.user.sso.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-30 14:32
 */
@WebFilter
@Slf4j
public class CourseFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user", userDTO);
        log.info("CourseFilter login user: {}", userDTO);
    }
}
