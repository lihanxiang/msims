INSERT INTO course (courseCode, faculty, name, credit, teacher, duration, time, description) 
VALUES ("CS003", "FIT", "SOFTWARE ENGINEERING", 4, "Subrota Kumar Mondal", "September 4th - December 17", "Monday@16:00-17:45, Friday@11:00-12:45", "description");

INSERT INTO course (courseCode, faculty, name, credit, teacher, duration, time, description) 
VALUES ("CS014", "FIT", "PROJECTS ON SOFTWARE ENGINEERING", 4, "Subrota Kumar Mondal", "September 4th - December 17", "Monday@14:00-15:45, Friday@09:00-10:45", "description");

INSERT INTO course (courseCode, faculty, name, credit, teacher, duration, time, description) 
VALUES ("CN102", "FIT", "COMPUTER NETWORKS APPLIED TECHNOLOGIES", 2, "鄭澤峰", "September 4th - December 17", "Tuesday@11:00-12:45", "description");

INSERT INTO course (courseCode, faculty, name, credit, teacher, duration, time, description) 
VALUES ("CN106", "FIT", "NETWORK PROGRAMMING", 4, "趙慶林", "September 4th - December 17", "Thursday@09:00-12:35", "description");

update user set faculty = "FIT" WHERE id = 2;

delete FROM file where id = 1;