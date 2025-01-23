package com.software.courseScheduler.DeadLine;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Deadline")
public class DeadlineModel {
	@Id
	Long id;
	Long student_id;
	int weight;
	Timestamp end_time;

	public Long getId() {
		return id;
	}

	// getter
	public Long getStudentId() {
		return student_id;
	}

	public int getWeight() {
		return weight;
	}

	public Timestamp getEndTime() {
		return end_time;
	}

	// setter

	public void setStudentId(Long student_id) {
		this.student_id = student_id;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setEndTime(Timestamp end_time) {
		this.end_time = end_time;
	}

}
