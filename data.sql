delete from course where id = 2;

INSERT INTO course (courseCode, faculty, courseName, credit, teacher, duration, time, description) 
VALUES ("CS003", "FIT", "SOFTWARE ENGINEERING", "4", "teacher", "September 4th - December 17", "Monday@16:00-17:45, Friday@11:00-12:45", "description");

INSERT INTO course (courseCode, faculty, courseName, credit, teacher, duration, time, description) 
VALUES ("CS014", "FIT", "PROJECTS ON SOFTWARE ENGINEERING", "4", "teacher", "September 4th - December 17", "Monday@14:00-15:45, Friday@09:00-10:45", "description");

INSERT INTO course (courseCode, faculty, courseName, credit, teacher, duration, time, description) 
VALUES ("CN102", "FIT", "COMPUTER NETWORKS APPLIED TECHNOLOGIES", "2", "鄭澤峰", "September 4th - December 17", "Tuesday@11:00-12:45", "description");

INSERT INTO course (courseCode, faculty, courseName, credit, teacher, duration, time, description) 
VALUES ("CN106", "FIT", "NETWORK PROGRAMMING", "4", "趙慶林", "September 4th - December 17", "Thursday@09:00-12:35", "description");

INSERT into student_course (studentId, courseCode) VALUES ("123", "CS003");

INSERT into student_course (studentId, courseCode) VALUES ("123", "CS014");

insert into gpa (courseCode, courseName, userId, credit, grade) VALUES ("CS003", "SOFTWARE ENGINEERING", "123", "4", "A");
insert into gpa (courseCode, courseName, userId, credit, grade) VALUES ("CS014", "PROJECTS ON SOFTWARE ENGINEERING", "123", "4", "B+");



insert into comment(courseCode, pid, commenterId, commenter, respondentId, content, type, date)
values ('CS003', 0, 6, 'teacher', 0, 'first comment', 'comment', '2019-12-09 21:39:00');
insert into comment(courseCode, pid, commenterId, commenter, respondentId, content, type, date)
values ('CS003', 0, 6, 'teacher', 0, 'second comment', 'comment', '2019-12-09 21:39:10');
insert into comment(courseCode, pid, commenterId, commenter, respondentId, content, type, date)
values ('CS003', 1, 2, '123', 6, 'first reply', 'reply', '2019-12-09 21:39:30');
insert into comment(courseCode, pid, commenterId, commenter, respondentId, content, type, date)
values ('CS003', 1, 2, '123', 6, 'second reply', 'reply', '2019-12-09 21:39:40');
