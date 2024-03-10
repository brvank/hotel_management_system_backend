#database setup

create database hms;

use hms;

create table table_user (
	user_id int PRIMARY KEY,
    user_name varchar(255) UNIQUE,
    user_password varchar(255),
    user_level  int
);

#drop table table_user;

insert into table_user (user_name, user_password, user_level) values ('subuser', 'subuser', 2);
