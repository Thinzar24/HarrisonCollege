package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Iterable<Student> findAllByClasses(Set<Class> classes);
}
