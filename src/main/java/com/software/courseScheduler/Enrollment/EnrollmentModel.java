package com.software.courseScheduler.Enrollment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Enrollment")
public class EnrollmentModel {
	@Id
	Long course_id;
	Long student_id;
	int year;

	// getters
	public Long getCourseId() {
		return course_id;
	}

	public Long getStudentId() {
		return student_id;
	}

	public int getYear() {
		return year;
	}

	// setters
	public void setStudentId(Long student_id) {
		this.student_id = student_id;
	}

	public void setCourseId(Long course_id) {
		this.course_id = course_id;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
