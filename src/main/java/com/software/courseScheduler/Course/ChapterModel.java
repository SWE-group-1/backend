package com.software.courseScheduler.Course;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Chapter")
public class ChapterModel {
	@Id
	Long chapter_id;
	@Column("chapter_number")
	Long chapterNumber;
	String name;
	@Column("page_length")
	int pageLength;
	int weight;
	@Column("course_id")
	Long courseId;

	// getters
	public Long getChapterId() {
		return chapter_id;
	}

	public String getName() {
		return name;
	}

	public int getPageLength() {
		return pageLength;
	}

	public int getWeight() {
		return weight;
	}

	public Long getCourseId() {
		return courseId;
	}

	public Long getChapterNumber() {
		return chapterNumber;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setPageLength(int page_length) {
		this.pageLength = page_length;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setCourseId(Long course_id) {
		this.courseId = course_id;
	}

	public void setChapterNumber(Long chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

}
