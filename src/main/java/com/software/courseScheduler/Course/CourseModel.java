package com.software.courseScheduler.Course;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Course")
public class CourseModel {
	@Id
	@Column("course_id")
	Long courseId;
	String name;
	String status; // current, passed(completed), failed
	int credit_hour;
	int weight;

	// getters
	public Long getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	public int getCreditHour() {
		return credit_hour;
	}

	public int getWeight() {
		return weight;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreditHour(int credit_hour) {
		this.credit_hour = credit_hour;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
