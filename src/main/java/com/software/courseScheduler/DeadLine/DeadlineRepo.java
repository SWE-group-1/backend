package com.software.courseScheduler.DeadLine;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadlineRepo extends ReactiveCrudRepository<DeadlineModel, Long> {
}
