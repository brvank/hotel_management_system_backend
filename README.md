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


create table table_room (
    room_id int not null primary key auto_increment,
    room_category varchar(50) not null,
    room_info varchar(50),
    room_price float not null
);

create table table_booking(
    booking_id int not null primary key auto_increment,
    guest_name varchar(50) not null,
    phone_number varchar(50) not null,
    date_time_check_in datetime not null,
    date_time_check_out datetime not null,
    person_count int not null,
    room_id int not null,
    total_price float not null,
    advance_amount float not null,
    gst float not null
);