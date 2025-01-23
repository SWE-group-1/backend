package com.software.courseScheduler.Enrollment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Enrollment")
public class EnrollmentModel {
	@Id
	Long id;
	@Column("course_id")
	Long courseId;
	@Column("student_id")
	Long studentId;
	int year;

	// getters
	public Long getCourseId() {
		return courseId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public int getYear() {
		return year;
	}

	// setters
	public void setStudentId(Long student_id) {
		this.studentId = student_id;
	}

	public void setCourseId(Long course_id) {
		this.courseId = course_id;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
