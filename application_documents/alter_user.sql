use harrison;

select * from user;

alter table user add name text;

update user set name = concat(first_name,' ',last_name);

alter table user drop first_name, drop last_name;

update user set name = 'Gail Forcewind' where id=9;
update user set username = 'GForcewind' where id=9;