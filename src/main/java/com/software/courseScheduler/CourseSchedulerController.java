package com.software.courseScheduler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.software.courseScheduler.Course.ChapterModel;
import com.software.courseScheduler.Course.CourseModel;
import com.software.courseScheduler.Enrollment.EnrollmentModel;
import com.software.courseScheduler.Student.StudentModel;
import com.software.courseScheduler.TimeBlock.TimeBlockModel;

import reactor.core.publisher.Mono;

@RestController
public class CourseSchedulerController {
	@Autowired
	public CourseSchedulerService service;

	@GetMapping("/students")
	public Mono<ResponseEntity<Map<String, List<StudentModel>>>> getAllStudents() {
		return service.getAllStudents();
	}

	@GetMapping("/students/{studentId}")
	public Mono<ResponseEntity<Map<StudentModel, List<CourseModel>>>> getStudentProfile(@PathVariable Long id) {
		return service.getStudentProfileById(id);
	}

	@PostMapping("/students")
	public Mono<ResponseEntity<Map<String, String>>> addStudent(@RequestBody StudentModel student) {
		return service.addStudent(student);
	}

	@PutMapping("/students/{studentId}")
	public Mono<ResponseEntity<Map<String, String>>> updateStudentProfile(@RequestBody StudentModel student) {
		return service.updateStudent(student);
	}

	@GetMapping("/student/{studentId}/courses")
	public Mono<ResponseEntity<Map<String, List<CourseModel>>>> getAllCourse(@PathVariable Long studentId) {
		return service.getAllCourse(studentId);
	}

	@PostMapping("/courses")
	public Mono<ResponseEntity<Map<String, String>>> addCourseForStudent(@RequestBody EnrollmentModel enrollment) {
		return service.enrollCourse(enrollment);
	}

	@GetMapping("/courses/{courseId}")
	public Mono<ResponseEntity<Map<CourseModel, List<ChapterModel>>>> getCourseDetails(@PathVariable Long courseId) {
		return service.getCourseById(courseId);
	}

	@PostMapping("/timeblock")
	public Mono<ResponseEntity<Map<String, String>>> addTimeBlock(@RequestBody TimeBlockModel timeblock) {
		return service.createTimeBlock(timeblock);
	}
}
