package com.example.demo.Repository;

import com.example.demo.Beans.Course;
import com.example.demo.Beans.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findByCourseName(String course_name);
    ArrayList<Course> findAllBySubject(Subject subject);
}
