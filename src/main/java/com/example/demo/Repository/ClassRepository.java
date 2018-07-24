package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Course;
import com.example.demo.Beans.Instructor;
import com.example.demo.Beans.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ClassRepository extends CrudRepository<Class, Long> {
    Iterable<Class> findAllBySemester(String semester);
    Iterable<Class> findAllByInstructor(Instructor instructor);
    Iterable<Class> findAllByStudents(Set<Student> students);
    Iterable<Class> findAllByCourse(Course course);
    Iterable<Class> findAllByCourseAndSemester(Course course, String semester);
    Iterable<Class> findAllByInstructorAndSemester(Instructor instructor, String semester);
    Iterable<Class> findAllByTimeAndSemester(String time, String semester);

}
