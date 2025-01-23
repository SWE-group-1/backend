package com.software.courseScheduler.Course;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Chapter")
public class ChapterModel {
	@Id
	Long chapter_id;
	String name;
	int page_length;
	int weight;
	Long couser_id;

	// getters
	public Long getChapterId() {
		return chapter_id;
	}

	public String getName() {
		return name;
	}

	public int getPageLength() {
		return page_length;
	}

	public int getWeight() {
		return weight;
	}

	public Long getCouserId() {
		return couser_id;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setPageLength(int page_length) {
		this.page_length = page_length;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setCouserId(Long couser_id) {
		this.couser_id = couser_id;
	}

}
