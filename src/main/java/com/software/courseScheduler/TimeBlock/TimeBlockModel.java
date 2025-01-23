package com.software.courseScheduler.TimeBlock;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Timeblock")
public class TimeBlockModel {
	@Id
	Long id;
	Long student_id;
	String week_day;
	int week_number;
	int priority;

	Timestamp start_time;
	Timestamp end_time;

	// getters
	public Long getId() {
		return id;
	}

	public Long getStudentId() {
		return student_id;
	}

	public int getPriority() {
		return priority;
	}

	public String getWeekDay() {
		return week_day;
	}

	public int getWeekNumber() {
		return week_number;
	}

	public Timestamp getStartTime() {
		return start_time;
	}

	public Timestamp getEndTime() {
		return end_time;
	}

	// setter
	public void setStudentId(Long student_id) {
		this.student_id = student_id;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setWeekDay(String week_day) {
		this.week_day = week_day;
	}

	public void setWeekNumber(int week_number) {
		this.week_number = week_number;
	}

	public void setStartTime(Timestamp start_time) {
		this.start_time = start_time;
	}

	public void setEndTime(Timestamp end_time) {
		this.end_time = end_time;
	}

}
