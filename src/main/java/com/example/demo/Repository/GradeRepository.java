package com.example.demo.Repository;

import com.example.demo.Beans.Grade;
import com.example.demo.Beans.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    ArrayList<Grade> findAllByStudent(Student student);
}
