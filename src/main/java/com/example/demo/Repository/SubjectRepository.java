package com.example.demo.Repository;

import com.example.demo.Beans.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findBySubjectName(String subject_name);
}
