package com.example.demo;

import com.example.demo.Beans.*;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.lang.Class;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    ClassRepository classRepository;

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
            student.setEntryYear(request.getParameter("entry_year"));
            student.setStudentNumber(request.getParameter("student_number"));
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
    public String updateToAdmin(@PathParam("id") long id)
    {
        User user = userRepository.findById(id).get();
        String oldRole = getOldRole(user);

        if(oldRole.equalsIgnoreCase("Student")){
            studentRepository.deleteById(id);
            user.setRoles(setNewRole(1,"Admin"));
            userRepository.save(user);
        }
//        User user = userRepository.findById(id).get();
//        Role oldRole = user.getRoles();
//        long role_id = Long.parseLong(request.getParameter("role_type"));
//        Role role = roleRepository.findById(role_id).get();
//        user.setRoles(role);
//        userRepository.save(user);
//
//        if(preRole.getRole().equalsIgnoreCase("Student"))
//        {
//
//            studentRepository.delete();
//
//        }else if (preRole.getRole().equalsIgnoreCase("Instructor"))
//        {
//            instructorRepository.delete();
//        }
//
//        if(role.getRole().equalsIgnoreCase("student"))
//        {
//            return "/register";
//        }else if (role.getRole().equalsIgnoreCase("instructor"))
//        {
//            return "/addInstructor";
//        }
        return "redirect:/users";
    }

    // receives User object, returns old role of that user
    private String getOldRole(User user) {
        String oldRole = "";

        while(user.getRoles().iterator().hasNext()){
            oldRole = user.getRoles().iterator().next().getRole();
        }

        return oldRole;
    }

    // receives role id and role name, returns collection of roles
    private Collection<Role> setNewRole(long id, String role_name) {
        Role role = new Role();
        role.setId(id);
        role.setRole(role_name);
        Collection<Role> roles = new HashSet<>();
        roles.add(role);

        return roles;
    }

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
        model.addAttribute("classes", classRepository.findAllBySemester("current"));
        return "classes";
    }

    @GetMapping("/adminsearch")
    public String getAdminSearch(){
        return "admin/adminsearch";
    }

    @PostMapping("/classesByInstructor")
    public String getClassesByInstructor(Model model, @RequestParam("instructorname1") String instructor_name) {
        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);
        model.addAttribute("classes", classRepository.findAllByInstructor(instructor));
        return "classes";
    }

    @PostMapping("/classesByStudent")
    public String getClassesByStudent(Model model, @RequestParam("studentname1") String student_name){
        User user = userRepository.findByName(student_name);
        Student student = studentRepository.findByUser(user);

        Set<Student> students = new HashSet<>();
        students.add(student);

        // This only returns one class even though it's more than one
        model.addAttribute("classes", classRepository.findAllByStudents(students));
        return "classes";
    }

    @PostMapping("/classesTaughtByInstructor")
    public String getClassesTaughtByInstructor(Model model, @RequestParam("instructorname2") String instructor_name) {

        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);

        model.addAttribute("classes", classRepository.findAllByInstructorAndSemester(instructor,"past"));

        return "classes";
    }

    @PostMapping("/classesByCourse")
    public String getClassesByCourse(Model model, @RequestParam("course1") String course_name) {
        Course course = courseRepository.findByCourseName(course_name);
        model.addAttribute("classes", classRepository.findAllByCourse(course));
        return "classes";
    }

    @PostMapping("/classesBySubjectInCurrentSemester")
    public String getClassesBySubjectInCurrentSemester(Model model, @RequestParam("subject") String subject_name) {
        Subject subject = subjectRepository.findBySubjectName(subject_name);
        ArrayList<Course> courses = courseRepository.findAllBySubject(subject);
        ArrayList<com.example.demo.Beans.Class> classes = new ArrayList<>();

        while(courses.iterator().hasNext()){
            Course course = courses.iterator().next();
            while(classRepository.findAllByCourse(course).iterator().hasNext()){
                com.example.demo.Beans.Class aClass = classRepository.findAllByCourse(course).iterator().next();
                classes.add(aClass);
            }
        }

        model.addAttribute("classes", classes);
        return "classes";
    }
}