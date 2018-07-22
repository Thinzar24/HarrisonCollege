package com.example.demo.Beans;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String subject_name;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Course> courses;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Class> classes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }
}
