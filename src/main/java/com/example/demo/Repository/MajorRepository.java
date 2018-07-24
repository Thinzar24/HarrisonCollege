package com.example.demo.Repository;

import com.example.demo.Beans.Department;
import com.example.demo.Beans.Major;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
<<<<<<< HEAD

public interface MajorRepository extends CrudRepository<Major, Long> {
    ArrayList<Major> findAllByDepartment(Department department);
    //Major findByMajor_name(String name);
=======


public interface MajorRepository extends CrudRepository<Major, Long> {
    ArrayList<Major> findAllByDepartment(Department department);

>>>>>>> 897e0634b2ff05df95264910078892921c237208
}
