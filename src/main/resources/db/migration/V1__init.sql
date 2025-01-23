CREATE TABLE Timeblocks (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	student_id BIGINT,
	week_day varchar(10),
	week_number smallint,
	start_time timestamp,
	end_time timestamp,
	priority INT,
	FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE
);

CREATE TABLE Student (
	student_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	first_name varchar(100),
	last_name varchar(100),
	department varchar(100),
	year int
);

CREATE TABLE Course (
	course_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name varchar(100),
	credit_hour INT
);

CREATE TABLE Chapter (
	chapter_id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name varchar(100),
	page_length INT,
	weight INT,
	course_id INT,
	FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

CREATE TABLE Deadline (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	student_id BIGINT,
	weight INT,
	end_time TIMESTAMP,
	FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE
);
