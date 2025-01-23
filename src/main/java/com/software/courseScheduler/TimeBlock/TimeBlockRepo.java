package com.software.courseScheduler.TimeBlock;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface TimeBlockRepo extends ReactiveCrudRepository<TimeBlockModel, Long> {
	Flux<TimeBlockModel> findAllByOrderByStartTimeAsc();
}
