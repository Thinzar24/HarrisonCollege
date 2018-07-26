use harrison;

select * from class;
select * from classroom;
select * from course;
select * from department;
select * from grade;
select * from instructor;
select * from major;
select * from student;
select * from subject;
select * from role;

-- List of users and their roles
select username, a.name, a.id, role_id, c.role from user a 
inner join user_roles b on a.id = b.user_id 
inner join role c on b.role_id = c.id;

-- List of students and classes they signed up for
select name, d.id as class_id, course_name, crn, days, time, semester
from user a
inner join student b on a.id = b.user_id
inner join student_class c on b.id = c.student_id
inner join class d on c.class_id = d.id
inner join course e on d.course_id = e.id;

-- List of students with classes and their grades
select name, course_name, grade, crn, days, time, semester
from user a
inner join student b on a.id = b.user_id
inner join student_class c on b.id = c.student_id
inner join class d on c.class_id = d.id
inner join course e on d.course_id = e.id
inner join grade f on b.id = f.student_id
where f.class_id = d.id;

-- List of classes that an instructor has taught
select c.instructor_id, name, c.id as class_id, course_name, crn, days, time, semester
from user a
inner join instructor b on a.id = b.user_id
inner join class c on c.instructor_id = b.id
inner join course d on c.course_id = d.id
where semester = 'past';

-- List of classes by course
select subject_name, course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
inner join subject c on b.subject_id = c.id
where semester = 'current'
order by course_name;

-- List of classes by subject in current semester
select subject_name, course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
inner join subject c on b.subject_id = c.id
where semester = 'current';

-- List of classes by instructor in current semester
select b.id as instructor_id, name, course_name, crn, days, time, semester
from user a
inner join instructor b on a.id = b.user_id
inner join class c on c.instructor_id = b.id
inner join course d on c.course_id = d.id
where semester = 'current'
order by instructor_id;

-- List of classes by certain time in current semester
select course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
where semester = 'current'
order by time;

-- List of classes by department in current semester
select department_name, course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
inner join major c on b.major_id = c.id
inner join department d on c.department_id = d.id
where semester = 'current'
order by department_name;

-- List of classrooms by instructor
select name, building_name, room_number, capacity, disabled
from classroom a
inner join class b on a.id = b.classroom_id
inner join instructor c on b.instructor_id = c.id
inner join user d on c.user_id = d.id
order by d.name;

-- List of classrooms by student
select d.name, building_name, room_number, capacity, disabled
from classroom a
left outer join class b on a.id = b.classroom_id
left outer join student_class e on b.id = es.class_id
inner join student c on e.student_id = c.id
inner join user d on c.user_id = d.id
order by d.name;

-- List of classrooms by course
select course_name, building_name, room_number, capacity, a.disabled
from classroom a
inner join class b on a.id = b.classroom_id
inner join course c on b.course_id = c.id
order by course_name;

-- List of courses by department
select department_name, course_name, course_description, credits
from course a
inner join major b on a.major_id = b.id
inner join department c on b.department_id = c.id;

-- List of majors by department
select department_name, major_name
from major a
inner join department b on a.department_id = b.id;

-- Get classes with students that don't have a grade
select a.id as class_id, a.crn, instructor_id, c.name as instructor_name, e.grade
from class a
inner join instructor b on a.instructor_id = b.id
inner join user c on b.user_id = c.id
inner join student_class d on a.id = d.class_id
left outer join grade e on d.class_id = e.class_id;

select * from user;

