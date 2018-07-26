package com.example.demo;

import com.example.demo.Beans.Role;
import com.example.demo.Beans.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        Run method will be executed after the application context is
        loaded and right before the Spring Application run method is
        completed.
     */
    @Override
    public void run(String... strings) throws Exception{
        System.out.println("Loading data...");

   //loadData();

    }

    private void loadData(){
        roleRepository.save(new Role("Admin"));
        roleRepository.save(new Role("Student"));
        roleRepository.save(new Role("Instructor"));
        roleRepository.save(new Role("Advisor"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role studentRole = roleRepository.findByRole("Student");
        Role instructorRole = roleRepository.findByRole("Instructor");
        Role advisorRole = roleRepository.findByRole("Advisor");

        User user = new User("password", "Mario Speedwagon", true, "MSpeedwagon");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Petey Cruiser", true, "PCruiser");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Paul Molive", true, "PMolive");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Anna Mull", true, "AMull");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Paige Turner", true, "PTurner");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Bob Frapples", true, "BFrapples");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Walter Melon", true, "WMelon");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Greta Life", true, "GLife");
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Gail Forcewind", true, "GForcewind");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Tara Zona", true, "TZona");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Barry Cade", true, "BCade");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Marvin Gardens", true, "MGardens");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Arty Ficial", true, "AFicial");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Tara Misu", true, "TMisu");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Dan Druff", true, "DDruff");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Gene Poole", true, "GPoole");
        user.setRoles(Arrays.asList(instructorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Bobby Pin", true, "advisor");
        user.setRoles(Arrays.asList(advisorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        user = new User("password", "Mal Practice", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
