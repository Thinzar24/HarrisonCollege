package com.example.demo.Repository;

import com.example.demo.Beans.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Department findByDepartmentName(String department_name);
}
