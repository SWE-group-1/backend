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
	public final CourseSchedulerService service;

	@Autowired
	public CourseSchedulerController(CourseSchedulerService service) {
		this.service = service;
	}

	@GetMapping("/students")
	public Mono<ResponseEntity<Map<String, List<StudentModel>>>> getAllStudents() {
		return service.getAllStudents();
	}

	@GetMapping("/students/{studentId}")
	public Mono<ResponseEntity<Map<String, Object>>> getStudentProfile(@PathVariable Long studentId) {
		return service.getStudentProfileById(studentId);
	}

	@PostMapping("/students")
	public Mono<ResponseEntity<Map<String, String>>> addStudent(@RequestBody StudentModel student) {
		return service.addStudent(student);
	}

	@PutMapping("/students/{studentId}")
	public Mono<ResponseEntity<Map<String, String>>> updateStudentProfile(@RequestBody StudentModel student,
			@PathVariable Long studentId) {
		return service.updateStudent(student, studentId);
	}

	@GetMapping("/students/{studentId}/courses")
	public Mono<ResponseEntity<Map<String, List<CourseModel>>>> getAllCourse(@PathVariable Long studentId) {
		return service.getAllCourseByStudent(studentId);
	}

	@PostMapping("/courses")
	public Mono<ResponseEntity<Map<String, String>>> addCourseForStudent(@RequestBody EnrollmentModel enrollment) {
		return service.enrollCourse(enrollment);
	}

	@GetMapping("/courses")
	public Mono<ResponseEntity<Map<String, List<CourseModel>>>> getAllCourse() {
		return service.getAllCourse();
	}

	@PostMapping("/students/{studentId}/addCourse")
	public Mono<ResponseEntity<Map<String, String>>> addCourseForStudent2(@RequestBody CourseModel course,
			@PathVariable Long studentId) {
		return service.addCourseToStudent(course, studentId);
	}

	@PostMapping("/courses/{courseId}/chapter/{chapterNumber}")
	public Mono<ResponseEntity<Map<String, String>>> addChapterToCourse(@RequestBody ChapterModel chapterModel,
			@PathVariable Long courseId, @PathVariable Long chapterNumber) {
		return service.addChapter(chapterModel, courseId, chapterNumber);
	}

	@GetMapping("/courses/{courseId}/chapter/{chapterNumber}")
	public Mono<ResponseEntity<Map<String, Object>>> getChaptersToCourse(@PathVariable Long courseId,
			@PathVariable Long chapterNumber) {
		return service.getChaptersForCourse(courseId, chapterNumber);
	}

	@GetMapping("/courses/{courseId}")
	public Mono<ResponseEntity<Map<String, Object>>> getCourseDetails(@PathVariable Long courseId) {
		return service.getCourseById(courseId);
	}

	@PostMapping("/timeblock")
	public Mono<ResponseEntity<Map<String, String>>> addTimeBlock(@RequestBody TimeBlockModel timeblock) {
		return service.createTimeBlock(timeblock);
	}

	@GetMapping("/timeblock")
	public Mono<ResponseEntity<Map<String, Object>>> getTimeBlocks() {
		return service.getTimeBlock();
	}

	@GetMapping("/timeblock/{timeblockId}")
	public Mono<ResponseEntity<Map<String, Object>>> getTimeBlockById(@PathVariable Long timeblockId) {
		return service.getTimeBlockById(timeblockId);
	}

	@GetMapping("/students/{studentId}/timeblocks")
	public Mono<ResponseEntity<Map<String, List<TimeBlockModel>>>> getAllTimeBlocksForStudent(
			@PathVariable Long studentId) {
		return service.getAllTimeblockForStudent(studentId);
	}
}
