package com.zxb.course;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-30 14:13
 */
@SpringBootApplication
@EnableDubboConfiguration
@ServletComponentScan
public class CourseEdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseEdgeApplication.class, args);
    }
}
