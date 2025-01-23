package com.software.courseScheduler.Course;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ChapterRepo extends ReactiveCrudRepository<ChapterModel, Long> {
	Flux<CourseModel> findByNameContainingIgnoreCase(String name);
}
