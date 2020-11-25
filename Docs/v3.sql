start transaction;

drop table if exists User_Slot_Course_Teacher;
drop table if exists User_Course;
drop table if exists Teacher_Slot_Course;
drop table if exists Teacher_Course;

drop table if exists Time_Slot;
drop table if exists Course;
drop table if exists Teacher;
drop table if exists User;

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

create table Time_Slot
(
    Day  varchar(9) not null,
    Hour tinyint    not null,
    constraint SlotTime_PK primary key (Day, Hour)
);

create table Teacher_Course
(
    EmailTeacher varchar(40) not null,
    TitleCourse  varchar(50) not null,
    constraint Teacher_Course_Pk primary key (EmailTeacher, TitleCourse),
    constraint Teacher_Course_FK_Teacher foreign key (EmailTeacher) references Teacher (Email) on delete cascade on update cascade,
    constraint Teacher_Course_FK_Course foreign key (TitleCourse) references Course (Title) on delete cascade on update cascade
);

create table Teacher_Slot_Course
(
    EmailTeacher varchar(40) not null,
    Day          varchar(9)  not null,
    Hour         tinyint     not null,
    TitleCourse  varchar(50) not null,
    constraint Teacher_Slot_Course_PK primary key (EmailTeacher, Day, Hour),
    constraint Teacher_Slot_Course_FK_Teacher_Course foreign key (EmailTeacher, TitleCourse) references Teacher_Course (EmailTeacher, TitleCourse) on delete cascade on update cascade,
    constraint Teacher_Slot_Course_FK_Time_Slot foreign key (Day, Hour) references Time_Slot (Day, Hour) on delete cascade on update cascade
);

create table User_Course
(
    EmailUser   varchar(40) not null,
    TitleCourse varchar(50) not null,
    constraint User_Course_Pk primary key (EmailUser, TitleCourse),
    constraint User_Course_FK_User foreign key (EmailUser) references User (Email) on delete cascade on update cascade,
    constraint User_Course_FK_Course foreign key (TitleCourse) references Course (Title) on delete cascade on update cascade
);

create table User_Slot_Course_Teacher
(
    EmailUser    varchar(40) not null,
    Day          varchar(9)  not null,
    Hour         tinyint     not null,
    TitleCourse  varchar(50) not null,
    EmailTeacher varchar(40) not null,
    constraint User_Slot_Course_Teacher_PK primary key (EmailUser, Day, Hour),
    constraint User_Slot_Course_Teacher_FK_User_Course foreign key (EmailUser, TitleCourse) references User_Course (EmailUser, TitleCourse) on delete cascade on update cascade,
    constraint User_Slot_Course_Teacher_FK_Teacher_Slot_Course foreign key (EmailTeacher, Day, Hour) references Teacher_Slot_Course (EmailTeacher, Day, Hour) on delete cascade on update cascade
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
insert into Time_Slot
values ('Lunedì', 15);
insert into Time_Slot
values ('Lunedì', 16);
insert into Time_Slot
values ('Lunedì', 17);
insert into Time_Slot
values ('Lunedì', 18);
insert into Time_Slot
values ('Lunedì', 19);
insert into Time_Slot
values ('Martedì', 15);
insert into Time_Slot
values ('Martedì', 16);
insert into Time_Slot
values ('Martedì', 17);
insert into Time_Slot
values ('Martedì', 18);
insert into Time_Slot
values ('Martedì', 19);
insert into Time_Slot
values ('Mercoledì', 15);
insert into Time_Slot
values ('Mercoledì', 16);
insert into Time_Slot
values ('Mercoledì', 17);
insert into Time_Slot
values ('Mercoledì', 18);
insert into Time_Slot
values ('Mercoledì', 19);
insert into Time_Slot
values ('Giovedì', 15);
insert into Time_Slot
values ('Giovedì', 16);
insert into Time_Slot
values ('Giovedì', 17);
insert into Time_Slot
values ('Giovedì', 18);
insert into Time_Slot
values ('Giovedì', 19);
insert into Time_Slot
values ('Venerdì', 15);
insert into Time_Slot
values ('Venerdì', 16);
insert into Time_Slot
values ('Venerdì', 17);
insert into Time_Slot
values ('Venerdì', 18);
insert into Time_Slot
values ('Venerdì', 19);
commit;


start transaction;
insert into Teacher_Course
values ('luca.roversi@unito.it', 'Programmazione I');
insert into Teacher_Course
values ('luca.roversi@unito.it', 'Programmazione II');
insert into Teacher_Course
values ('luca.roversi@unito.it', 'Algoritmi e Strutture Dati');
insert into Teacher_Course
values ('enrico.bini@unito.it', 'Sistemi Operativi');
insert into Teacher_Course
values ('enrico.bini@unito.it', 'Architettura degli Elaboratori');
insert into Teacher_Course
values ('marco.aldinucci@unito.it', 'Architettura degli Elaboratori');
insert into Teacher_Course
values ('marco.aldinucci@unito.it', 'Sistemi Operativi');
insert into Teacher_Course
values ('liliana.ardissono@unito.it', 'Programmazione III');
insert into Teacher_Course
values ('liliana.ardissono@unito.it', 'Tecnologie Web');
insert into Teacher_Course
values ('luca.padovani@unito.it', 'Algoritmi e Strutture Dati');
insert into Teacher_Course
values ('luca.padovani@unito.it', 'Linguaggi Formali e Traduttori');
commit;

start transaction;
insert into Teacher_Slot_Course
values ('luca.padovani@unito.it', 'Lunedì', 15, 'Linguaggi Formali e Traduttori');
insert into Teacher_Slot_Course
values ('luca.padovani@unito.it', 'Lunedì', 16, 'Algoritmi e Strutture Dati');
commit;

start transaction;
insert into User_Course
values ('raul.palade@edu.unito.it', 'Algoritmi e Strutture Dati');
insert into User_Course
values ('raul.palade@edu.unito.it', 'Programmazione III');
insert into User_Course
values ('raul.palade@edu.unito.it', 'Linguaggi Formali e Traduttori');
commit;

start transaction;
insert into User_Slot_Course_Teacher
values ('raul.palade@edu.unito.it', 'Lunedì', 15, 'Linguaggi Formali e Traduttori', 'luca.padovani@unito.it');
commit;

-- Query Users
select *
from User;

-- Query Teachers
select *
from Teacher;

-- Query Courses
select *
from Course;

-- Insert Course
insert into Course
values (?);
-- Remove Course
delete
from Course
where Title = ?;

-- Insert User
insert into User
values (?, ?, ?, ?, ?);
-- Remove User
delete
from User
where Email = ?;

-- Insert Teacher
insert into Teacher
values (?, ?, ?, ?);
-- Remove Teacher
delete
from Teacher
where Email = ?;

-- Assign Course to Teacher
insert into Teacher_Course
values (?, ?);
-- Remove Course to Teacher
delete
from Teacher_Course
where EmailTeacher = ?
  and TitleCourse = ?;

-- View Teacher by Course
select Name, Surname, TitleCourse
from Teacher
         JOIN teacher_course tc on teacher.Email = tc.EmailTeacher
         join course c on c.Title = tc.TitleCourse;

-- Insert Booking
-- Utente deve prenotare una lezione User -> User_Course
-- Docente deve insegnare il corso Teacher -> Teacher_Course
-- Docente deve essere disponibile in slot orario Teacher -> Teacher_Slot_Course
-- Utente prenota lezione in User_Slot_Course_Teacher

-- insertBooking(raul, palade, raul@gmal.com, roversi@gmail.com, lunedi, 15)
delimiter $$
create procedure insert1(IN EmailUser VARCHAR(40), IN Day varchar(9), IN Hour tinyint, IN TitleCourse VARCHAR(50),
                         IN EmailTeacher varchar(40))
begin
    insert ignore into teacher_slot_course
    values (EmailTeacher, Day, Hour, TitleCourse);
    insert ignore into user_slot_course_teacher
    values (EmailUser, Day, Hour, TitleCourse, EmailTeacher);
end $$
delimiter ;
-- Remove Booking

call insert1(?, ?, ?, ?, ?);

replace into user_course
values (?, ?);

insert ignore into user_slot_course_teacher
values (?, ?, ?, ?, ?);