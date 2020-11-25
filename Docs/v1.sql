start transaction;

drop table if exists Booking;
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

create table Booking
(
    EmailUser    varchar(40) not null,
    EmailTeacher varchar(40) not null,
    TitleCourse  varchar(50) not null,
    Day          varchar(9)  not null,
    Hour         tinyint     not null,
    constraint Booking_PK primary key (EmailUser, EmailTeacher, TitleCourse, Day, Hour),
    constraint Booking_FK_User foreign key (EmailUser) references User (Email) on delete cascade on update cascade,
    constraint Booking_FK_Teacher foreign key (EmailTeacher, TitleCourse) references Teaches (EmailTeacher, TitleCourse) on delete cascade on update cascade,
    constraint Booking_FK_SlotTime foreign key (Day, Hour) references SlotTime (Day, Hour) on delete cascade on update cascade,
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
values ('Lunedì', 15);
insert into SlotTime
values ('Lunedì', 16);
insert into SlotTime
values ('Lunedì', 17);
insert into SlotTime
values ('Lunedì', 18);
insert into SlotTime
values ('Lunedì', 19);
insert into SlotTime
values ('Martedì', 15);
insert into SlotTime
values ('Martedì', 16);
insert into SlotTime
values ('Martedì', 17);
insert into SlotTime
values ('Martedì', 18);
insert into SlotTime
values ('Martedì', 19);
insert into SlotTime
values ('Mercoledì', 15);
insert into SlotTime
values ('Mercoledì', 16);
insert into SlotTime
values ('Mercoledì', 17);
insert into SlotTime
values ('Mercoledì', 18);
insert into SlotTime
values ('Mercoledì', 19);
insert into SlotTime
values ('Giovedì', 15);
insert into SlotTime
values ('Giovedì', 16);
insert into SlotTime
values ('Giovedì', 17);
insert into SlotTime
values ('Giovedì', 18);
insert into SlotTime
values ('Giovedì', 19);
insert into SlotTime
values ('Venerdì', 15);
insert into SlotTime
values ('Venerdì', 16);
insert into SlotTime
values ('Venerdì', 17);
insert into SlotTime
values ('Venerdì', 18);
insert into SlotTime
values ('Venerdì', 19);
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
insert into Booking
values ('raul.palade@edu.unito.it', 'luca.roversi@unito.it', 'Programmazione II', 'Lunedì', '15');
insert into Booking
values ('raul.palade@edu.unito.it', 'luca.roversi@unito.it', 'Programmazione II', 'Lunedì', '16');
commit;