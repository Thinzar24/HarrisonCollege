package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Student;
import com.example.demo.Beans.StudentClass;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface StudentClassRepository extends CrudRepository<StudentClass, Long> {
    ArrayList<StudentClass> findAllByStudent(Student student);
    ArrayList<StudentClass> findAllByAClass(Class aClass);
}
