# HarrisonCollege
Harrison College
Harrison College is a small liberal-arts school in Harrison, Pennsylvania. In its 80 year history, Harrison has tried many new fads and trends, and in that same time period it has turned its back on just as many. Harrison has decided to get rid of its old system for tracking student enrollments and create its own.

Harrison has a standard system for recording and tracking its classes: each course has a course number and a subject code, name, description and number of credits. Each instructor has an employee number, name, department, and office number. Each student has a student number, name, major and year of entry. Harrison refers to the fact that they teach first semester English Composition as a course and the fact that this fall Dave Wolf is teaching freshman composition in Irech Hall at 8am on Mon and Wed as a class. Each class has a crn number, a course number & subject code, an instructor, and a classroom. Each classroom has a bldg name and a room number and a max capacity. Students can’t enroll themselves in a class that has reached capacity.

 

Each Instructor can teach anywhere from 0 to many classes.
Each class can have anywhere from 0 to many students.
Each course can have anywhere from 0 to many classes. (think instances)
Each department can have 1 to many majors.
You will need to allow for the 4 types of users: students, instructors, advisors and administrators.
 

Students can:

enroll in a class
drop a class
view their current schedule
view their unofficial transcript
buy an official transcript ($5)
view all course
view all classes in current semester
view all classes in a subject in current semester
view all classes by an instructor in a current semester
view all classes at a certain time in the current semester
view all courses in a department
view all current classes in a department
view all majors in a department
 

Instructors can:

view their classes in the current semester
get the roster of students in their classes from this semester or previous semesters
assign grades to students in their classes
view grade sheets from previous semesters
view all course
view all classes in current semester
view all classes in a subject in current semester
view all classes by an instructor in a current semester
view all classes at a certain time in the current semester
view all courses in a department
view all current classes in a department
view all majors in a department
 

Advisors can:

view a students transcript
enroll a student in a class
drop a student from a class
view all course
view all classes in current semester
view all classes in a subject in current semester
view all classes by an instructor in a current semester
view all classes at a certain time in the current semester
view all courses in a department
view all current classes in a department
view all majors in a department
 

Administrators can:

Create, update, list or disable a course
Create, update, list or disable a classroom
Create, update, list or disable a department
Create, update, list or disable a major
Add class to schedule for current or later semester
Remove class from schedule for current or later semester
Change a new users type to (student, instructor, advisor or administrator)
Override maximum enrollment hold
view all course
view all classes by an instructor
view all classes by a student
view a list of all students taught by an instructor
view a list of all instructors that have taught a class
view a list of all classes that an instructor has taught
view a list of all classes of a course
view a list of all classrooms used by a course
view a list of all classrooms used by an instructor
view a list of all classrooms used by a student
view all classes in current semester
view all classes in a subject in current semester
view all classes by an instructor in a current semester
view all classes at a certain time in the current semester
view all courses in a department
view all current classes in a department
view all majors in a department
 

Anyone should be able to signup and create an account, but they automatically become students, only an administrator can reclassify them.

 

*********************************************

IMPORTANT:

You will need sample data to develop and test your site. Please include this sample data as a SQL script so I can test your site. You can export MySQL data to SQL Scripts easily - let me know if you need to know how.

*********************************************

 

 

Examples 

Course

ENG 101    Freshman Composition   3    English Department

Class

78567    MWF  8am   Dave Wolf     Humanities Bldg Rm 321

Instructor

8675309    Dave Wolf    English Department   Office  HU322
