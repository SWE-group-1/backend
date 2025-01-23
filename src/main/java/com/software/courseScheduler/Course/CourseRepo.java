package com.software.courseScheduler.Course;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface CourseRepo extends ReactiveCrudRepository<CourseModel, Long> {
	Flux<CourseModel> findByNameContainingIgnoreCase(String name);
}
