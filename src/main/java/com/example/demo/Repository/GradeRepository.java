package com.example.demo.Repository;

import com.example.demo.Beans.Grade;
import org.springframework.data.repository.CrudRepository;

public interface GradeRepository extends CrudRepository<Grade, Long> {
}
