package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{

    /*@Autowired
    RoleRepository roleRepository;*/

    /*
        Run method will be executed after the application context is
        loaded and right before the Spring Application run method is
        completed.
     */
    @Override
    public void run(String... strings) throws Exception{
        System.out.println("Loading data...");
/*
        roleRepository.save(new Role("Admin"));
        roleRepository.save(new Role("Student"));
        roleRepository.save(new Role("Instructor"));
        roleRepository.save(new Role("Advisor")); */

    }
}
