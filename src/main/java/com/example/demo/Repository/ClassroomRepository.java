package com.example.demo.Repository;

import com.example.demo.Beans.Class;
import com.example.demo.Beans.Classroom;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ClassroomRepository extends CrudRepository<Classroom, Long> {
    Iterable<Classroom> findAllByClasses(Set<Class> classes);
}
