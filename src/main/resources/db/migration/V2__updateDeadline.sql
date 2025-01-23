ALTER TABLE Timeblocks 
ADD COLUMN "is_done" BOOL;

ALTER TABLE Student 
ADD COLUMN grade INT,
DROP COLUMN student_id,
ADD COLUMN id BIGINT PRIMARY KEY AUTO_INCREMENT,
ADD COLUMN student_id VARCHAR(50);

CREATE TABLE Enrollment (
  student_id BIGINT,
  course_id BIGINT,
  year INT,
  PRIMARY KEY (student_id, course_id) 
);

-- Drop existing foreign key constraints
ALTER TABLE Timeblocks 
DROP CONSTRAINT fk_timeblocks_student;

ALTER TABLE Deadline 
DROP CONSTRAINT fk_deadline_student;

-- Add new foreign key constraints
ALTER TABLE Timeblocks 
ADD CONSTRAINT fk_timeblocks_student 
FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE;

ALTER TABLE Deadline 
ADD CONSTRAINT fk_deadline_student 
FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE;
