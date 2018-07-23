package com.example.demo;

import com.example.demo.Beans.Major;
import com.example.demo.Beans.Role;
import com.example.demo.Beans.Student;
import com.example.demo.Beans.Subject;
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
import javax.websocket.server.PathParam;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    //@Autowired
    //ClassRepository classRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    GradeRepository gradeRepository;

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

    @GetMapping("/users")
    public String changeRole(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @RequestMapping("/updateRole/{id}")
    public String processChageRole(@PathParam("id") long id, HttpServletRequest request)
    {
        User user = userRepository.findById(id).get();
        //Role preRole = user.getRoles();
        long role_id = Long.parseLong(request.getParameter("role_type"));
        Role role = roleRepository.findById(role_id).get();
        user.setRoles(role);
        userRepository.save(user);

        if(preRole.getRole().equalsIgnoreCase("Student"))
        {

            studentRepository.delete();

        }else if (preRole.getRole().equalsIgnoreCase("Instructor"))
        {
            instructorRepository.delete();
        }

        if(role.getRole().equalsIgnoreCase("student"))
        {
            return "/register";
        }else if (role.getRole().equalsIgnoreCase("instructor"))
        {
            return "/addInstructor";
        }
        return "redirect:/adminmain";
    }







>>>>>>> 49f479481b9daf6ed0673eb340f83a7a677b72e2
    @GetMapping("/courseform")
    public String addCourse(){
        return "admin/courseform";
    }

    @GetMapping("/classroomform")
    public String addClassroom(){
        return "admin/classroomform";
    }

    @GetMapping("/departmentform")
    public String addDepartment(){
        return "admin/departmentform";
    }

    @GetMapping("/classform")
    public String addClass(){
        return "admin/classform";
    }

    @RequestMapping("/courses")
    public String getCourses(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        return "courses";
    }

    @RequestMapping("/classesInCurrentSemester")
    public String getCurrentClasses(Model model){
        //model.addAttribute("classes", classRepository.findAllBySemester("current"));
        return "classes";
    }
<<<<<<< HEAD

    @GetMapping("/adminsearch")
    public String getAdminSearch(){
        return "adminsearch";
    }
=======
>>>>>>> 49f479481b9daf6ed0673eb340f83a7a677b72e2

}