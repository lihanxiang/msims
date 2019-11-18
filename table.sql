create database msims;
use msims;

# General table
CREATE TABLE user (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(32) NOT NULL UNIQUE,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    gender VARCHAR(32) NOT NULL,
    faculty VARCHAR(32) NOT NULL,
    phone VARCHAR(32) NOT NULL,
    email VARCHAR(50) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    salt VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE course (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    courseCode VARCHAR(32) NOT NULL,
    faculty varchar(32) not null,
    name VARCHAR(32) NOT NULL,
    credit INT(2) NOT NULL,
    teacher VARCHAR(100) NOT NULL,
    duration VARCHAR(100) NOT NULL,
    time VARCHAR(255) NOT NULL,
    description TEXT NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE file (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    faculty VARCHAR(32) NOT NULL,
    fileId VARCHAR(32) NOT NULL,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

# Moddle tables
create table student_course(
	id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(32) NOT NULL,
    courseCode VARCHAR(32) NOT NULL
) ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE component (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    courseCode VARCHAR(32) NOT NULL,
    type VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE component_file (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    componentId INT(32) NOT NULL,
    fileId VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE bulletin_board (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    courseCode VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE bulletin_board_message (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    boardId INT(32) NOT NULL,
    content text NOT NULL,
    date VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE comment (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    courseCode VARCHAR(32) NOT NULL,
    pid INT(32) NOT NULL,
    commenterId INT(32) NOT NULL,
    respondentId INT(32) NOT NULL,
    content TEXT NOT NULL,
    type VARCHAR(32) NOT NULL,
    date VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

#CREATE TABLE  (
    
#)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE submission (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    assignmentId INT(32) NOT NULL,
    userId VARCHAR(32) NOT NULL,
    fileId VARCHAR(32) NOT NULL,
    author VARCHAR(32) NOT NULL,
    comment TEXT NOT NULL,
    isGraded INT(1) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE assessment (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    assignmentId INT(32) NOT NULL,
    submissionId INT(32) NOT NULL,
    score DOUBLE(5 , 2 ) NOT NULL,
    comment TEXT NOT NULL,
    date VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

# Portal
create table news(
	id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title varchar(32) not null,
    faculty varchar(32) not null,
    content varchar(32) not null,
    date varchar(32) not null
) ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;

CREATE TABLE news_file (
    id INT(32) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    newsId INT(32) NOT NULL,
    fileId VARCHAR(32) NOT NULL
)  ENGINE=INNODB , AUTO_INCREMENT=1 , CHARSET=UTF8;









