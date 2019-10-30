package com.zxb.course.repository;

import com.zxb.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-10-29 17:57
 */
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "select user_id from pr_user_course where course_id = ?1", nativeQuery = true)
    Integer getCourseByTeacher(@Param("courseId") int courseId);
}
