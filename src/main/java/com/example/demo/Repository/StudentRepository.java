package com.example.demo.Repository;

import com.example.demo.Beans.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
