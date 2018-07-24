package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Instructor;
import com.example.demo.Beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.Set;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    Instructor findByUser(User user);
    Iterator<Instructor> findAllByClasses(Set<Class> classes);
}
