package com.example.demo.Repository;

import com.example.demo.Beans.Instructor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
}
