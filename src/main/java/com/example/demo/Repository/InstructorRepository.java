package com.example.demo.Repository;

import com.example.demo.Beans.Instructor;
import com.example.demo.Beans.User;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
    Instructor findByUser(User user);
}
