package com.example.demo;

import com.example.demo.Beans.Major;
import com.example.demo.Beans.Student;
import com.example.demo.Beans.User;
import com.example.demo.Repository.*;
import org.hibernate.id.BulkInsertionCapableIdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model)
    {
        model.addAttribute("user", new User());
        model.addAttribute("majors", majorRepository.findAll());
        return "studentform";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute User user, BindingResult result, HttpServletRequest request)
    {
        if (result.hasErrors())
        {
            return "studentform";
        }
        else
        {
            Student student = new Student();
            student.setEntry_year(request.getParameter("entry_year"));
            student.setStudent_number(request.getParameter("student_number"));
            student.setMajor(majorRepository.findById(Long.parseLong(request.getParameter("major"))).get());
            student.setUser(user);
            studentRepository.save(student);
            userService.saveStudent(user);
        }
        return "redirect:/";
    }

    // Returns currently logged in user
    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }

    @RequestMapping("/studentmain")
    public String studentMain()
    {
        return "studentmain";
    }
    @RequestMapping("/instructormain")
    public String instructorMain()
    {
        return "instructormain";
    }
    @RequestMapping("/adminmain")
    public String adminMain()
    {
        return "admin/adminmain";
    }

    @RequestMapping("/advisormain")
    public String advisorMain()
    {
        return "advisormain";
    }

    @GetMapping("/addMajor")
    public String addMajor(Model model)
    {
        model.addAttribute("major", new Major());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/majorform";
    }

    @PostMapping("/addMajor")
    public String processMajor(@Valid @ModelAttribute Major major, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "majorform";
        }
        majorRepository.save(major);
        return "redirect:/adminmain";
    }

<<<<<<< HEAD
    @GetMapping("/users")
    public String changeRole(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

=======
    @GetMapping("/courseform")
    public String addCourse(){
        return "courseform";
    }

    @GetMapping("/classroomform")
    public String addClassroom(){
        return "classroomform";
    }

    @GetMapping("/departmentform")
    public String addDepartment(){
        return "departmentform";
    }

    @GetMapping("/classform")
    public String addClass(){
        return "classform";
    }

    @RequestMapping("/courses")
    public String getCourses(){
        return "courses";
    }

    @RequestMapping("/classesInCurrentSemester")
    public String getCurrentClasses(){
        return "classes";
    }
>>>>>>> 03bfd9acd9c96c01e95be218f2dbff19bc63f32e

}