package com.example.demo.Repository;

import com.example.demo.Beans.*;
import com.example.demo.Beans.Class;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Set;

public interface ClassRepository extends CrudRepository<Class, Long> {
    Iterable<Class> findAllBySemester(String semester);
    Iterable<Class> findAllByInstructor(Instructor instructor);
    Iterable<Class> findAllByCourse(Course course);
    Iterable<Class> findAllByCourseAndSemester(Course course, String semester);
    Iterable<Class> findAllByInstructorAndSemester(Instructor instructor, String semester);
    Iterable<Class> findAllByTimeAndSemester(String time, String semester);
    Iterable<Class> findAllByCrn(String crn);
}
