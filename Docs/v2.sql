start transaction;

drop table if exists Subscription;
drop table if exists TeacherBooking;
drop table if exists Teaches;

drop table if exists User;
drop table if exists Teacher;
drop table if exists Course;
drop table if exists SlotTime;

create table User
(
    Name          varchar(25) not null,
    Surname       varchar(15) not null,
    Email         varchar(40) not null,
    Password      char(161)   not null,
    Administrator bool        not null,
    constraint User_PK primary key (Email)
);

create table Teacher
(
    Name     varchar(25) not null,
    Surname  varchar(15) not null,
    Email    varchar(40) not null,
    Password char(161)   not null,
    constraint Teacher_PK primary key (Email)
);

create table Course
(
    Title varchar(50),
    constraint Course_PK primary key (Title)
);

create table SlotTime
(
    Day  varchar(9) not null,
    Hour tinyint    not null,
    constraint SlotTime_PK primary key (Day, Hour)
);

create table Teaches
(
    EmailTeacher varchar(40) not null,
    TitleCourse  varchar(50) not null,
    constraint Teaches_Pk primary key (EmailTeacher, TitleCourse),
    constraint Teaches_FK_Teacher foreign key (EmailTeacher) references Teacher (Email) on delete cascade on update cascade,
    constraint Teaches_FK_Course foreign key (TitleCourse) references Course (Title) on delete cascade on update cascade
);

create table TeacherBooking
(
    EmailTeacher varchar(40) not null,
    Day          varchar(9)  not null,
    Hour         tinyint     not null,
    TitleCourse  varchar(50) not null,
    constraint TeacherBooking_PK primary key (EmailTeacher, Day, Hour),
    constraint TeacherBooking_FK_Teaches foreign key (EmailTeacher, TitleCourse) references Teaches (EmailTeacher, TitleCourse) on delete cascade on update cascade,
    constraint TeacherBooking_FK_SlotTime foreign key (Day, Hour) references SlotTime (Day, Hour) on delete cascade on update cascade
);

create table Subscription
(
    EmailUser    varchar(40) not null,
    EmailTeacher varchar(40) not null,
    Day          varchar(9)  not null,
    Hour         tinyint     not null,
    constraint Booking_PK primary key (EmailUser, EmailTeacher, Day, Hour),
    constraint Booking_FK_User foreign key (EmailUser) references User (Email) on delete cascade on update cascade,
    constraint Booking_FK_TeacherBooking foreign key (EmailTeacher, Day, Hour) references TeacherBooking (EmailTeacher, Day, Hour) on delete cascade on update cascade,
    constraint Booking_UNIQUE unique (EmailTeacher, Day, Hour)
);

commit;


start transaction;
insert into User
values ('Raul', 'Palade', 'raul.palade@edu.unito.it', 'password', false);
insert into User
values ('Daniele', 'Pepe', 'daniele.pepe@edu.unito.it', 'password', false);
insert into User
values ('Giuseppe', 'Moramarco', 'giuseppe.moramarco@edu.unito.it', 'password', false);
insert into User
values ('Lorenzo', 'Scipioni', 'lorenzo.scipioni@edu.unito.it', 'password', false);
commit;


start transaction;
insert into Teacher
values ('Luca', 'Roversi', 'luca.roversi@unito.it', 'password');
insert into Teacher
values ('Marco', 'Aldinucci', 'marco.aldinucci@unito.it', 'password');
insert into Teacher
values ('Liliana', 'Ardissono', 'liliana.ardissono@unito.it', 'password');
insert into Teacher
values ('Enrico', 'Bini', 'enrico.bini@unito.it', 'password');
commit;
insert into Teacher
values ('Luca', 'Padovani', 'luca.padovani@unito.it', 'password');
commit;


start transaction;
insert into Course
values ('Programmazione I');
insert into Course
values ('Programmazione II');
insert into Course
values ('Programmazione III');
insert into Course
values ('Architettura degli Elaboratori');
insert into Course
values ('Tecnologie Web');
insert into Course
values ('Sistemi Operativi');
insert into Course
values ('Algoritmi e Strutture Dati');
insert into Course
values ('Linguaggi Formali e Traduttori');
commit;


start transaction;
insert into SlotTime
values ('Luned??', 15);
insert into SlotTime
values ('Luned??', 16);
insert into SlotTime
values ('Luned??', 17);
insert into SlotTime
values ('Luned??', 18);
insert into SlotTime
values ('Luned??', 19);
insert into SlotTime
values ('Marted??', 15);
insert into SlotTime
values ('Marted??', 16);
insert into SlotTime
values ('Marted??', 17);
insert into SlotTime
values ('Marted??', 18);
insert into SlotTime
values ('Marted??', 19);
insert into SlotTime
values ('Mercoled??', 15);
insert into SlotTime
values ('Mercoled??', 16);
insert into SlotTime
values ('Mercoled??', 17);
insert into SlotTime
values ('Mercoled??', 18);
insert into SlotTime
values ('Mercoled??', 19);
insert into SlotTime
values ('Gioved??', 15);
insert into SlotTime
values ('Gioved??', 16);
insert into SlotTime
values ('Gioved??', 17);
insert into SlotTime
values ('Gioved??', 18);
insert into SlotTime
values ('Gioved??', 19);
insert into SlotTime
values ('Venerd??', 15);
insert into SlotTime
values ('Venerd??', 16);
insert into SlotTime
values ('Venerd??', 17);
insert into SlotTime
values ('Venerd??', 18);
insert into SlotTime
values ('Venerd??', 19);
commit;


start transaction;
insert into Teaches
values ('luca.roversi@unito.it', 'Programmazione I');
insert into Teaches
values ('luca.roversi@unito.it', 'Programmazione II');
insert into Teaches
values ('luca.roversi@unito.it', 'Algoritmi e Strutture Dati');
insert into Teaches
values ('enrico.bini@unito.it', 'Sistemi Operativi');
insert into Teaches
values ('enrico.bini@unito.it', 'Architettura degli Elaboratori');
insert into Teaches
values ('marco.aldinucci@unito.it', 'Architettura degli Elaboratori');
insert into Teaches
values ('marco.aldinucci@unito.it', 'Sistemi Operativi');
insert into Teaches
values ('liliana.ardissono@unito.it', 'Programmazione III');
insert into Teaches
values ('liliana.ardissono@unito.it', 'Tecnologie Web');
insert into Teaches
values ('luca.padovani@unito.it', 'Algoritmi e Strutture Dati');
insert into Teaches
values ('luca.padovani@unito.it', 'Linguaggi Formali e Traduttori');
commit;

start transaction;
insert into TeacherBooking values ('luca.roversi@unito.it', 'Luned??', '15','Programmazione II');
insert into TeacherBooking values ('luca.roversi@unito.it', 'Luned??', '16','Programmazione II');
insert into TeacherBooking values ('luca.roversi@unito.it', 'Luned??', '16','Programmazione III');
commit;

start transaction;
insert into Subscription values ('raul.palade@edu.unito.it', 'luca.roversi@unito.it', 'Luned??', 15);
insert into Subscription values ('giuseppe.moramarco@edu.unito.it', 'luca.roversi@unito.it', 'Luned??', 16);
insert into Subscription values ('daniele.pepe@edu.unito.it', 'luca.roversi@unito.it', 'Luned??', 14);
begin;