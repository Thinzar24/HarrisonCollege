package com.example.demo.Repository;

import com.example.demo.Beans.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
