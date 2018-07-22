package com.example.demo.Beans;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String major_name;
    private boolean disabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @OneToMany(mappedBy = "major", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Student> students;

    @OneToMany(mappedBy = "major", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Course> courses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
