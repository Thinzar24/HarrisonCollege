use harrison;

drop table student_class;

create table studentclass(
	id bigint(20) not null primary key auto_increment,
	student_id bigint(20),
	class_id bigint(20)
)

insert into student_class (class_id, student_id) values (1,1);
insert into student_class (class_id, student_id) values (2,1);
insert into student_class (class_id, student_id) values (3,2);
insert into student_class (class_id, student_id) values (23,2);
insert into student_class (class_id, student_id) values (4,3);
insert into student_class (class_id, student_id) values (5,3);
insert into student_class (class_id, student_id) values (6,3);
insert into student_class (class_id, student_id) values (21,3);
insert into student_class (class_id, student_id) values (7,4);
insert into student_class (class_id, student_id) values (8,4);
insert into student_class (class_id, student_id) values (9,4);
insert into student_class (class_id, student_id) values (24,4);
insert into student_class (class_id, student_id) values (10,5);
insert into student_class (class_id, student_id) values (11,5);
insert into student_class (class_id, student_id) values (12,5);
insert into student_class (class_id, student_id) values (25,5);
insert into student_class (class_id, student_id) values (13,6);
insert into student_class (class_id, student_id) values (14,6);
insert into student_class (class_id, student_id) values (15,6);
insert into student_class (class_id, student_id) values (16,7);
insert into student_class (class_id, student_id) values (117,7);
insert into student_class (class_id, student_id) values (126,7);
insert into student_class (class_id, student_id) values (127,7);
insert into student_class (class_id, student_id) values (18,8);
insert into student_class (class_id, student_id) values (119,8);
insert into student_class (class_id, student_id) values (120,8);
insert into student_class (class_id, student_id) values (122,8);
