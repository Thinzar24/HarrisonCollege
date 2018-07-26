package com.example.demo;
import com.example.demo.Beans.*;
import com.example.demo.Beans.Class;
import com.example.demo.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTMLDocument;
import javax.validation.Valid;
import java.util.*;

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
    StudentClassRepository studentClassRepository;

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
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("majors", majorRepository.findAll());
        return "studentform";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute User user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "studentform";
        } else {
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
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }
/////////////////////////////////////////////////////////////AFTER LOGIN REDIRECT
    @RequestMapping("/studentmain")
    public String studentMain() {
        return "studentmain";
    }

    @RequestMapping("/instructormain")
    public String instructorMain() {
        return "instructormain";
    }

    @RequestMapping("/adminmain")
    public String adminMain() {
        return "admin/adminmain";
    }

    @RequestMapping("/advisormain")
    public String advisorMain() {
        return "advisormain";
    }

    /////////////////////////////////////////////////////////////////////////////////////////For MAJOR
    @GetMapping("/addMajor")
    public String addMajor(Model model) {
        model.addAttribute("major", new Major());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/majorform";
    }
    @PostMapping("/addMajor")
    public String processMajor(@Valid @ModelAttribute Major major, BindingResult result) {
        if (result.hasErrors()) {
            return "majorform";
        }
        majorRepository.save(major);
        return "redirect:/adminmain";
    }

    @RequestMapping("/listMajor")
    public String viewAllMajor(Model model)
    {
        model.addAttribute("majors", majorRepository.findAll());
        return "majors";
    }
    @RequestMapping("/updateMajor/{id}")
    public String updateMajor(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("major", majorRepository.findById(id));
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/majorform";
    }

    //////////////////////////////////////////////////////// For Classroom
    @GetMapping("/addClassroom")
    public String classroomForm(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "admin/classroomform";
    }
    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute Classroom classroom, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/classroomform";
        }
        classroomRepository.save(classroom);
        return "redirect:/adminmain";
    }

    @RequestMapping("/listClassroom")
    public String viewAllClassroom( Model model){
        model.addAttribute("classrooms",classroomRepository.findAll());
        return "admin/classrooms";
    }

    @RequestMapping("/updateClassroom/{id}")
    public String updateClassroom(@PathVariable("id")long id, Model model){
        model.addAttribute("classroom",classroomRepository.findById(id));
        return "admin/classroomform";
    }
    /////////////////////////////////////////////////////////////////////////////////////////////UPDATE ROLES
    @GetMapping("/users")
    public String changeRole(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }
    @RequestMapping("/updateToAdmin/{id}")
    public String updateToAdmin(@PathVariable("id") long id)
    {
        User user = userRepository.findById(id).get();
        String oldRole = getOldRole(user);
        if(oldRole.equalsIgnoreCase("Student")){
            long stdId = studentRepository.findByUser(user).getId();
            studentRepository.deleteById(stdId);
            user.setRoles(setNewRole(1,"Admin"));
            userRepository.save(user);
        } else if (oldRole.equalsIgnoreCase("instructor"))
        {
            long instId = instructorRepository.findByUser(user).getId();
            instructorRepository.deleteById(instId);
            user.setRoles(setNewRole(1, "Admin" ));
            userRepository.save(user);
        }
        else
        {
            user.setRoles(setNewRole(1, "Admin" ));
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    @RequestMapping("/updateToStudent/{id}")
    public String updateToStudent(@PathVariable("id") long id, Model model)
    {
        User user = userRepository.findById(id).get();
        String oldRole = getOldRole(user);
        if(oldRole.equalsIgnoreCase("Instructor")){
            long intId = instructorRepository.findByUser(user).getId();
            instructorRepository.deleteById(intId);
        }
        model.addAttribute("user", userRepository.findById(id));
        model.addAttribute("majors", majorRepository.findAll());
        return "studentform";
    }
    @GetMapping("/updateToInstructor/{id}")
    public String updateToInstructor(@PathVariable("id") long id, Model model)
    {
        User user = userRepository.findById(id).get();
        model.addAttribute("insturctor", new Instructor());
        model.addAttribute("user_id", user.getId());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/instructorform";
    }
    @PostMapping("/updateToInstructor")
    public String addInstructor(@Valid @ModelAttribute Instructor instructor, BindingResult result,HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            return "admin/instructorform";
        }
        long userId = Long.parseLong(request.getParameter("user_id"));
        User user = userRepository.findById(userId).get();
        String oldRole = getOldRole(user);
        if(oldRole.equalsIgnoreCase("Student")){
            long stdId = studentRepository.findByUser(user).getId();
            studentRepository.deleteById(stdId);
        }
        user.setRoles(setNewRole(3,"Instructor"));
        instructor.setUser(user);
        userRepository.save(user);
        System.out.println("@@@@@@@@@@@@@@@@@");
        instructorRepository.save(instructor);
        System.out.println("################# AFTER ADD TO INSTRUCTOR");
        return "redirect:/users";
    }
    @RequestMapping("/updateToAdvisor/{id}")
    public String updateToAdvisor(@PathVariable("id") long id)
    {
        User user = userRepository.findById(id).get();
        String oldRole = getOldRole(user);
        if(oldRole.equalsIgnoreCase("Student")){
            long stdId = studentRepository.findByUser(user).getId();
            studentRepository.deleteById(stdId);
            user.setRoles(setNewRole(4,"Advisor"));
            userRepository.save(user);
        } else if (oldRole.equalsIgnoreCase("instructor"))
        {
            long instId = instructorRepository.findByUser(user).getId();
            instructorRepository.deleteById(instId);
            user.setRoles(setNewRole(4, "Advisor" ));
            userRepository.save(user);
        }
        else
        {
            user.setRoles(setNewRole(4, "Advisor" ));
            userRepository.save(user);
        }
        return "redirect:/users";
    }
    // receives User object, returns old role of that user
    private String getOldRole(User user) {
        String oldRole = "";

        Iterator<Role> it = user.getRoles().iterator();
        while(it.hasNext()){
            Role role = it.next();
            oldRole = role.getRole();
            it.remove();
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

////////////////////////////////////////////////////////////////////////////////////////////For Course
    @GetMapping("/addCourse")
    public String addCourse(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("majors", majorRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/courseform" ;
    }

    @PostMapping("/addCourse")
    public String processCourse(@Valid @ModelAttribute Course course, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "admin/courseform";
        }
        courseRepository.save(course);
        return "redirect:/adminmain";
    }
    @RequestMapping("/listCourse")
    public String viewAllCourse(Model model)
    {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses";
    }
    @RequestMapping("/updateCourse/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("course", courseRepository.findById(id));
        model.addAttribute("majors", majorRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "admin/courseform";
    }

//////////////////////////////////////////////////////////////////////////////////////////For Class

    @GetMapping("/addClass")
    public String addClass(Model model) {
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("class", new Class());
        model.addAttribute("courses", courseRepository.findAll());
    return "admin/classform" ;
}

    @PostMapping("/addClass")
    public String processClass(@Valid @ModelAttribute Class aclass, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "admin/classform";
        }
        classRepository.save(aclass);
        return "redirect:/adminmain";
    }
    @RequestMapping("/listClass")
    public String viewAllClass(Model model)
    {
        model.addAttribute("classes", classRepository.findAll());
        return "classes";
    }
    @RequestMapping("/updateClass/{id}")
    public String updateClass(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("class", classRepository.findById(id));
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "admin/classform";
    }

    @RequestMapping("/deleteClass/{id}")
    public String deleteClass(@PathVariable("id")long id){
        classRepository.deleteById(id);
        return "admin/classes";
    }

    @RequestMapping("/listClassToEnrollByStudent")
    public String getClassToEnrollByStudent(Model model){
        Student student = studentRepository.findByUser(getUser());

        model.addAttribute("displayEnroll",true);
        model.addAttribute("displayDrop",false);
        model.addAttribute("classes", getClassListToEnroll(student));
        return "classes";
    }

    private ArrayList<Class> getClassListToEnroll(Student student) {
        ArrayList<Class> classList = new ArrayList<>();

        Iterable<Class> classes = classRepository.findAllBySemester("current");
        Iterator<Class> classIterator = classes.iterator();

        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
        Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

        // Classes student currently enrolled in
        ArrayList<Class> classesStudent = new ArrayList<>();

        // Loop through studentClasses to get classes student
        // currently enrolled in
        while(studentClassIterator.hasNext()) {
            Class aClass = studentClassIterator.next().getaClass();
            classesStudent.add(aClass);
            studentClassIterator.remove();
        }

        while(classIterator.hasNext()){
            Class aClass = classIterator.next();
            if(!classesStudent.contains(aClass)){
                classList.add(aClass);
            }
            classIterator.remove();
        }

        return  classList;
    }

    @RequestMapping("/enrollClassForStudent/{id}")
    public String enrollClassStudent(@PathVariable("id") long id)
    {
        // Get student
        Student student = studentRepository.findByUser(getUser());
        // Get class to enroll in
        Class aClass = classRepository.findById(id).get();

        StudentClass studentClass = new StudentClass(student,aClass);
        studentClassRepository.save(studentClass);

        return "redirect:/viewScheduleStudent";

//        User user = getUser();
//        Student student = studentRepository.findByUser(user);
//        ArrayList<Class> classList = getSchedule(student);
//        Class aClass = classRepository.findById(id).get();
//        classList.add(aClass);
//        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
//        StudentClass studentClass = new StudentClass(student, aClass);
//        studentClasses.add(studentClass);
//        student.setStudentClasses(studentClasses);
//        return "redirect:/studentmain";
    }

    @RequestMapping("/dropClassForStudent/{id}")
    public String dropClassStudent(@PathVariable("id") long id)
    {
        // Get student
        Student student = studentRepository.findByUser(getUser());

        // Get class to drop
        Class aClass = classRepository.findById(id).get();

        // Get student/class record and remove it
        StudentClass studentClass = studentClassRepository.findByStudentAndAClass(student,aClass);
        studentClassRepository.deleteById(studentClass.getId());

        return "redirect:/viewScheduleStudent";
//        User user = getUser();
//        Student student = studentRepository.findByUser(user);
//        ArrayList<Class> classList = getSchedule(student);
//        Class aClass = classRepository.findById(id).get();
//        classList.remove(aClass);
//        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
//        studentClasses.remove(studentClassRepository.findByStudentAndAClass(student,aClass));
//        student.setStudentClasses(studentClasses);
//        return "redirect:/studentmain";
    }

    @RequestMapping("/getTranscript")
    public String getTranscriptByStudent(Model model){
        return "transcript";
    }

    @RequestMapping("/enrollClassForAdvisor/{id}")
    public String enrollClassAdvisor(@PathVariable("id") long id, HttpServletRequest request)
    {
//        long studentId= Long.parseLong(request.getParameter("student_id"));
//        Student student = studentRepository.findById(studentId).get();
//        ArrayList<Class> classList = getSchedule(student);
//        Class aClass = classRepository.findById(id).get();
//        classList.add(aClass);
//        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
//        StudentClass studentClass = new StudentClass(student, aClass);
//        studentClasses.add(studentClass);
//        student.setStudentClasses(studentClasses);
        return "redirect:/advisormain";
    }

    @RequestMapping("/dropClassForAdvisor/{id}}")
    public String dropClassAdvisor(@PathVariable("id") long id, HttpServletRequest request)
    {
//        long studentId= Long.parseLong(request.getParameter("student_id"));
//        Student student = studentRepository.findById(studentId).get();
//        ArrayList<Class> classList = getSchedule(student);
//        Class aClass = classRepository.findById(id).get();
//        classList.remove(aClass);
//        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
//        studentClasses.remove(studentClassRepository.findByStudentAndAClass(student,aClass));
//        student.setStudentClasses(studentClasses);
        return "redirect:/advisormain";
    }

//    @RequestMapping("/viewStudentScheduleStudent")
//    public String viewStudentSchedule(Model model)
//    {
//        User user = getUser();
//        Student student = studentRepository.findByUser(user);
//        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
//        Set<Class> classes = new HashSet<>();
//        for(int i=0; i<studentClasses.size(); i++)
//        {
//            classes.add(studentClasses.get(i).getaClass());
//        }
//        model.addAttribute("classes", classes);
//        return "classes";
//    }


//////////////////////////////////////////////////////////////////////////////////////////FOR Department

    @GetMapping("/addDepartment")
    public String addDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "admin/departmentform";
    }

    @PostMapping("/addDepartment")
    public String processDepartment(@Valid @ModelAttribute Department department, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "admin/departmentform";
        }
        departmentRepository.save(department);
        return "redirect:/adminmain";
    }
    @RequestMapping("/listDepartment")
    public String viewAllDepartment(Model model)
    {
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/departments";
    }
    @RequestMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("department", departmentRepository.findById(id));
        return "admin/departmentform";
    }

///////////////////////////////////////////////////////////////////////////////////////////// FOR QUERY


    @RequestMapping("/classesInCurrentSemester")
    public String getCurrentClasses(Model model) {
        model.addAttribute("classes", classRepository.findAllBySemester("current"));
        return "classes";
    }

    @GetMapping("/search")
    public String getSearch() {
        return "search";
    }

    @PostMapping("/classesByInstructor")
    public String getClassesByInstructor(Model model, @RequestParam("instructorname1") String instructor_name) {
        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);
        model.addAttribute("classes", classRepository.findAllByInstructor(instructor));
        return "classes";
    }

    @PostMapping("/classesByStudent")
    public String getClassesByStudent(Model model, @RequestParam("studentname1") String student_name) {
        Student student = studentRepository.findByUser(userRepository.findByName(student_name));
        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
        ArrayList<Class> classList = new ArrayList<>();

        Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

        while(studentClassIterator.hasNext()) {
            StudentClass studentClass = studentClassIterator.next();
            Class aClass = studentClass.getaClass();
            classList.add(aClass);
            studentClassIterator.remove();
        }

        model.addAttribute("classes",classList);
        return "classes";
    }

    @PostMapping("/classesTaughtByInstructor")
    public String getClassesTaughtByInstructor(Model model, @RequestParam("instructorname2") String instructor_name) {

        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);

        model.addAttribute("classes", classRepository.findAllByInstructorAndSemester(instructor, "past"));

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

        Iterator<Course> courseIterator = courses.iterator();

        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            Iterator<com.example.demo.Beans.Class> classIterator = classRepository.findAllByCourseAndSemester(course, "current").iterator();
            while (classIterator.hasNext()) {
                com.example.demo.Beans.Class aClass = classIterator.next();
                classes.add(aClass);
                classIterator.remove();
            }
            courseIterator.remove();
        }

        model.addAttribute("classes", classes);
        return "classes";
    }

    @PostMapping("/classesByInstructorInCurrentSemester")
    public String getClassesByInstructorInCurrentSemester(Model model, @RequestParam("instructorname3") String instructor_name) {
        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);

        model.addAttribute("classes", classRepository.findAllByInstructorAndSemester(instructor, "current"));

        return "classes";
    }

    @RequestMapping("/instructorCurrentClasses")
    public String getInstructorCurrentClasses(Model model){
        User user = userRepository.findById(getUser().getId()).get();
        Instructor instructor = instructorRepository.findByUser(user);
        model.addAttribute("classes_title","Classes " + getUser().getName() + " is Currently Teaching");
        model.addAttribute("classes", classRepository.findAllByInstructorAndSemester(instructor, "current"));
        return "classes";
    }

    @RequestMapping("/instructorPastClasses")
    public String getInstructorPastClasses(Model model){
        User user = userRepository.findById(getUser().getId()).get();
        Instructor instructor = instructorRepository.findByUser(user);
        model.addAttribute("classes_title","Classes " + getUser().getName() + " Has Taught");
        model.addAttribute("classes", classRepository.findAllByInstructorAndSemester(instructor, "past"));
        return "classes";
    }

    @RequestMapping("/viewStudentInClass/{id}")
    public String getStudentsInClass(@PathVariable("id") long id, Model model) {
        Class aClass = classRepository.findById(id).get();
        ArrayList<Student> students = new ArrayList<>();

        Set<StudentClass> studentClasses = aClass.getStudentClasses();
        Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

        while(studentClassIterator.hasNext()){
            Student student = studentClassIterator.next().getStudent();
            students.add(student);
            studentClassIterator.remove();
        }

        model.addAttribute("page_title", "Students in " + aClass.getCourse().getCourseName() + ", CRN: " + aClass.getCrn());
        model.addAttribute("students", students);
        return "students";
    }

    @PostMapping("/classesByTimeInCurrentSemester")
    public String getClassesByTimeInCurrentSemester(Model model, @RequestParam("class_time") String class_time) {
        model.addAttribute("classes", classRepository.findAllByTimeAndSemester(class_time, "current"));
        return "classes";
    }

    @PostMapping("/classesByDepartment")
    public String getClassesByDepartment(Model model, @RequestParam("department1") String department_name) {

        Department department = departmentRepository.findByDepartmentName(department_name);
        ArrayList<Major> majors = majorRepository.findAllByDepartment(department);
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<com.example.demo.Beans.Class> classes = new ArrayList<>();

        Iterator<Major> majorIterator = majors.iterator();
        while (majorIterator.hasNext()) {
            Major major = majorIterator.next();
            courses = courseRepository.findAllByMajor(major);
            Iterator<Course> courseIterator = courses.iterator();

            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                Iterator<com.example.demo.Beans.Class> classIterator = classRepository.findAllByCourseAndSemester(course, "current").iterator();
                while (classIterator.hasNext()) {
                    com.example.demo.Beans.Class aClass = classIterator.next();
                    classes.add(aClass);
                    classIterator.remove();
                }
                courseIterator.remove();
            }
            majorIterator.remove();
        }
        model.addAttribute("classes", classes);
        return "classes";
    }

    @PostMapping("/classroomsByCourse")
    public String getClassroomsByCourse(Model model, @RequestParam("course") String course_name){
        Course course = courseRepository.findByCourseName(course_name);

        Set<com.example.demo.Beans.Class> classSet = new HashSet<>();
        Iterable<com.example.demo.Beans.Class> classes = classRepository.findAllByCourse(course);
        Iterator<com.example.demo.Beans.Class> classIterator = classes.iterator();

        ArrayList<Classroom> classrooms = new ArrayList<>();

        while(classIterator.hasNext()){
            com.example.demo.Beans.Class aClass = classIterator.next();
            classSet.add(aClass);
            Classroom classroom = classroomRepository.findByClasses(classSet);
            if(!classrooms.contains(classroom)) {
                classrooms.add(classroom);
            }
            classSet.remove(aClass);
            classIterator.remove();
        }

        model.addAttribute("title_type", "Classrooms used by " + course_name);
        model.addAttribute("classrooms", classrooms);
        return "admin/classrooms";
    }

    @PostMapping("/classroomsByInstructor")
    public String getClassroomsByInstructor(Model model, @RequestParam("instructorname4") String instructor_name) {
        User user = userRepository.findByName(instructor_name);
        Instructor instructor = instructorRepository.findByUser(user);

        Set<com.example.demo.Beans.Class> classSet = new HashSet<>();
        Iterable<com.example.demo.Beans.Class> classes = classRepository.findAllByInstructor(instructor);
        Iterator<com.example.demo.Beans.Class> classIterator = classes.iterator();

        ArrayList<Classroom> classrooms = new ArrayList<>();

        while(classIterator.hasNext()){
            com.example.demo.Beans.Class aClass = classIterator.next();
            classSet.add(aClass);
            Classroom classroom = classroomRepository.findByClasses(classSet);
            classrooms.add(classroom);
            classSet.remove(aClass);
            classIterator.remove();
        }

        model.addAttribute("title_type", instructor_name);
        model.addAttribute("classrooms", classrooms);
        return "admin/classrooms";
    }

    @PostMapping("/classroomsByStudent")
    public String getClassroomsByStudent(Model model, @RequestParam("studentname") String student_name) {
        Student student = studentRepository.findByUser(userRepository.findByName(student_name));
        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
        ArrayList<Classroom> classroomList = new ArrayList<>();

        Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

        while(studentClassIterator.hasNext()) {
            StudentClass studentClass = studentClassIterator.next();
            Classroom classroom = studentClass.getaClass().getClassroom();
            if(!classroomList.contains(classroom)) {
                classroomList.add(classroom);
            }
            studentClassIterator.remove();
        }

        model.addAttribute("title_type", "Classrooms used by " +student_name);
        model.addAttribute("classrooms", classroomList);
        return "admin/classrooms";
    }

    @PostMapping("/coursesByDepartment")
    public String getCoursesByDepartment(Model model, @RequestParam("department2") String department_name){
        Department department = departmentRepository.findByDepartmentName(department_name);
        ArrayList<Major> majors = majorRepository.findAllByDepartment(department);
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Course> courseList = new ArrayList<>();

        Iterator<Major> majorIterator = majors.iterator();

        while (majorIterator.hasNext()) {
            Major major = majorIterator.next();
            courses = courseRepository.findAllByMajor(major);
            Iterator<Course> courseIterator = courses.iterator();

            while (courseIterator.hasNext()) {
                Course course = courseIterator.next();
                courseList.add(course);
                courseIterator.remove();
            }
            majorIterator.remove();
        }

        model.addAttribute("courses",courseList);
        return "courses";

    }

    @PostMapping("/majorsByDepartment")
    public String getMajorsByDepartment(Model model, @RequestParam("department") String department_name){
        Department department = departmentRepository.findByDepartmentName(department_name);

        model.addAttribute("majors",majorRepository.findAllByDepartment(department));
        return "majors";
    }

    @RequestMapping("/viewScheduleStudent")
    public String getStudentSchedule(Model model){
        Student student = studentRepository.findByUser(getUser());
        model.addAttribute("classes_title","Schedule for " + student.getUser().getName());
        model.addAttribute("displayEnroll",false);
        model.addAttribute("displayDrop",true);
        model.addAttribute("classes",getSchedule(student));
        return "classes";
    }

    @RequestMapping("/viewScheduleAdvisor/{id}")
    public String getStudentScheduleByAdisor(@PathVariable("id") long id, Model model){
        Student student = studentRepository.findById(id).get();
        model.addAttribute("classes_title","Schedule for " + student.getUser().getName());
        model.addAttribute("displayEnroll",false);
        model.addAttribute("displayDrop",true);
        model.addAttribute("classes",getSchedule(student));
        return "classes";
    }

    private ArrayList<Class> getSchedule(Student student){
        ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByStudent(student);
        ArrayList<Class> classList = new ArrayList<>();

        Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

        while(studentClassIterator.hasNext()) {
            StudentClass studentClass = studentClassIterator.next();
            Class aClass = studentClass.getaClass();
            if(aClass.getSemester().equalsIgnoreCase("current")) {
                classList.add(aClass);
            }
            studentClassIterator.remove();
        }

        return classList;
    }

    @RequestMapping("/viewStudents")
    public String getStudentsByAdvisor(Model model) {
        model.addAttribute("students",studentRepository.findAll());
        return "students";
    }

    @PostMapping("/studentsByInstructor")
    public String getStudentsByInstructor(Model model, @RequestParam("instructorname") String instructor_name){
        Instructor instructor = instructorRepository.findByUser(userRepository.findByName(instructor_name));

        ArrayList<Student> students = new ArrayList<>();

        Iterable<Class> classes = classRepository.findAllByInstructor(instructor);
        Iterator<Class> classIterator = classes.iterator();

        while(classIterator.hasNext()){
            ArrayList<StudentClass> studentClasses = studentClassRepository.findAllByAClass(classIterator.next());
            Iterator<StudentClass> studentClassIterator = studentClasses.iterator();

            while(studentClassIterator.hasNext()){
                Student student = studentClassIterator.next().getStudent();
                if(!students.contains(student)){
                    students.add(student);
                }
                studentClassIterator.remove();
            }
            classIterator.remove();
        }

        model.addAttribute("page_title", "Students Taught by " + instructor_name);
        model.addAttribute("students",students);
        return "students";
    }

    @PostMapping("/instructorsByClass")
    public String getInstructorsByClass(Model model, @RequestParam("crn") String class_crn){
        model.addAttribute("classes_title", "Instructors for Class CRN " + class_crn);
        model.addAttribute("classes", classRepository.findAllByCrn(class_crn));
        return "classes";
    }

}