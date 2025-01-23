-- Create the Student table
CREATE TABLE Student (
    id BIGSERIAL PRIMARY KEY,
    student_id VARCHAR(50), 
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    department VARCHAR(100),
    year INT,
    grade FLOAT      
);

-- Create the Course table
CREATE TABLE Course (
    course_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    credit_hour INT
);

-- Create the Chapter table
CREATE TABLE Chapter (
    chapter_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    page_length INT,
    weight INT,
    course_id BIGINT, 
    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

-- Create the Timeblocks table
CREATE TABLE Timeblocks (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT,
    week_day VARCHAR(10),
    week_number SMALLINT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    priority INT,
    id_done BOOLEAN, 
    FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE
);

-- Create the Deadline table
CREATE TABLE Deadline (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT,
    weight INT,
    end_time TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE
);

-- Create the Enrollment table
CREATE TABLE Enrollment (
    student_id BIGINT,
    course_id BIGINT,
    year INT,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);
