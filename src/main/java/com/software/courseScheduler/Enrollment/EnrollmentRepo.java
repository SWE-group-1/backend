package com.software.courseScheduler.Enrollment;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.software.courseScheduler.Course.CourseModel;
import com.software.courseScheduler.Student.StudentModel;

import reactor.core.publisher.Flux;

@Repository
public interface EnrollmentRepo extends ReactiveCrudRepository<EnrollmentModel, Long> {
	Flux<StudentModel> findByCourseId(Long courseId);

	Flux<CourseModel> findByStudentId(Long studentId);
}
