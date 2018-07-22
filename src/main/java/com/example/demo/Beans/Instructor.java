package com.example.demo.Beans;

import com.example.demo.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String employee_number;
    private String office_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="department_id")
    private Department department;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    public Set<Class> classes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
