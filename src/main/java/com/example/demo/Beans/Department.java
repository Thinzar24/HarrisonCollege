package com.example.demo.Beans;


import javax.persistence.*;
import java.util.Set;


@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String department_name;

    private boolean disabled;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Major> majors;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Instructor> instructors;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Set<Major> getMajors() {
        return majors;
    }

    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }
}
