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
select username, a.name, a.id, role_id, c.role from user a 
inner join user_roles b on a.id = b.user_id 
inner join role c on b.role_id = c.id;
select * from role;

-- List of students and classes they signed up for
select name, course_name, f.id, crn, days, time, semester
from user a
inner join student b on a.id = b.user_id
inner join student_class c on b.id = c.student_id
inner join class d on c.class_id = d.id
inner join course e on d.course_id = e.id
inner join instructor f on d.instructor_id = f.id;

-- List of classes that an instructor has taught
select name, course_name, crn, days, time, semester
from user a
inner join instructor b on a.id = b.user_id
inner join class c on c.instructor_id = b.id
inner join course d on c.course_id = d.id
where semester = 'past';

-- List of classes by subject in current semester
select subject_name, course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
inner join subject c on b.subject_id = c.id
where semester = 'current';

-- List of classes by instructor in current semester
select name, course_name, crn, days, time, semester
from user a
inner join instructor b on a.id = b.user_id
inner join class c on c.instructor_id = b.id
inner join course d on c.course_id = d.id
where semester = 'current'
order by time;

-- List of classes by department in current semester
select department_name, course_name, crn, days, time, semester
from class a
inner join course b on a.course_id = b.id
inner join major c on b.major_id = c.id
inner join department d on c.department_id = d.id
where semester = 'current';

select * from user;

