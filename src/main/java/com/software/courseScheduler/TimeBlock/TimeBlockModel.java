package com.software.courseScheduler.TimeBlock;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Timeblocks")
public class TimeBlockModel {
	@Id
	Long id;
	@Column("student_id")
	Long studentId;
	String week_day;
	int week_number;
	int priority;

	@Column("start_time")
	Timestamp startTime;
	@Column("end_time")
	Timestamp endTime;

	// getters
	public Long getId() {
		return id;
	}

	public Long getStudentId() {
		return studentId;
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
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	// setter
	public void setStudentId(Long student_id) {
		this.studentId = student_id;
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
		this.startTime = start_time;
	}

	public void setEndTime(Timestamp end_time) {
		this.endTime = end_time;
	}

}
