package com.software.courseScheduler.Student;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface StudentRepo extends ReactiveCrudRepository<StudentModel, Long> {
	Flux<StudentModel> findByFirstNameContainingIgnoreCase(String name);
}
