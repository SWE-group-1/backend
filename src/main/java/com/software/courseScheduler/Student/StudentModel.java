package com.software.courseScheduler.Student;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Student")
public class StudentModel {
	@Id
	Long id;
	@Column("student_id")
	String studentId;
	String first_name;
	String last_name;
	String department;
	int year;
	float grade;

	public Long getId() {
		return id;
	}

	public String getStudentId() {
		return studentId;
	}

	public String getFirstName() {
		return first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public String getDepartment() {
		return department;
	}

	public int getYear() {
		return year;
	}

	public float getGrade() {
		return grade;
	}

	public void setStudentId(String student_id) {
		this.studentId = student_id;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

}
