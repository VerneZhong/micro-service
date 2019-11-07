package com.zxb.course.filter;

import com.zxb.thrift.user.dto.UserDTO;
import com.zxb.user.sso.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-30 14:32
 */
@WebFilter
@Slf4j
public class CourseFilter extends LoginFilter {

    @Value("${user.edge.service.addr}")
    private String userEdgeServiceAddr;

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user", userDTO);
        log.info("CourseFilter login user: {}", userDTO);
    }

    @Override
    protected String userEdgeServiceAddr() {
        return userEdgeServiceAddr;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(CourseFilter courseFilter) {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(courseFilter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        filterFilterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterFilterRegistrationBean;
    }
}
