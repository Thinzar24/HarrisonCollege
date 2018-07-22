package com.example.demo.Repository;

import com.example.demo.Beans.Classroom;
import org.springframework.data.repository.CrudRepository;

public interface ClassroomRepository extends CrudRepository<Classroom, Long> {
}
