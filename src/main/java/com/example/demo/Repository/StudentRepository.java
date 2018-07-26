package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Student;
import com.example.demo.Beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Set;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findByUser(User user);
}
