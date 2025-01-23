package com.software.courseScheduler;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.software.courseScheduler.Course.*;
import com.software.courseScheduler.DeadLine.*;
import com.software.courseScheduler.Enrollment.EnrollmentModel;
import com.software.courseScheduler.Enrollment.EnrollmentRepo;
import com.software.courseScheduler.Student.*;
import com.software.courseScheduler.TimeBlock.*;

import reactor.core.publisher.Mono;

@Service
public class CourseSchedulerService {
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private ChapterRepo chapterRepo;
	@Autowired
	private StudentRepo studentRepo;
	@Autowired
	private EnrollmentRepo enrollmentRepo;
	@Autowired
	private DeadlineRepo deadlineRepo;
	@Autowired
	private TimeBlockRepo timeBlockRepo;
	private final Duration StudyTime = Duration.ofHours(1);

	public Mono<ResponseEntity<Map<String, String>>> addCourse(CourseModel course) {
		return courseRepo
				.save(course)
				.flatMap(savedCourse -> Mono.just(
						ResponseEntity.ok(
								Map.of("message", "Course succesfully added to database"))));
	}

	public Mono<ResponseEntity<Map<String, List<CourseModel>>>> getAllCourse(Long studentId) {
		return enrollmentRepo
				.findAll()
				.filter(enrollment -> enrollment.getStudentId() == studentId)
				.flatMap(enrollment -> courseRepo.findById(enrollment.getCourseId()))
				.collectList()
				.flatMap(course -> Mono.just(
						ResponseEntity.ok(
								Map.of("course", course))));
	}

	public Mono<ResponseEntity<Map<CourseModel, List<ChapterModel>>>> getCourseById(Long courseId) {
		return courseRepo.findById(courseId)
				.flatMap(course -> chapterRepo.findByCourseId(courseId)
						.collectList()
						.flatMap(chapters -> Mono.just(ResponseEntity.ok().body(Map.of(course, chapters)))));
	}

	public Mono<ResponseEntity<Map<String, String>>> addStudent(StudentModel student) {
		return studentRepo
				.save(student)
				.flatMap(savedStudent -> Mono.just(
						ResponseEntity.ok(
								Map.of("message", "Student succesfully added to database"))));
	}

	public Mono<ResponseEntity<Map<String, String>>> updateStudent(StudentModel student) {
		return studentRepo.findById(student.getId())
				.flatMap(existingStudent -> {
					existingStudent.setYear(student.getYear());
					existingStudent.setGrade(student.getGrade());
					existingStudent.setFirstName(student.getFirstName());
					existingStudent.setLastName(student.getLastName());
					existingStudent.setDepartment(student.getDepartment());
					return studentRepo.save(existingStudent).flatMap(savedStudent -> Mono.just(
							ResponseEntity.ok(
									Map.of("message", "Student succesfully updated profile"))));
				});
	}

	public Mono<ResponseEntity<Map<String, List<StudentModel>>>> getAllStudents() {
		return studentRepo
				.findAll()
				.collectList()
				.flatMap(student -> Mono.just(
						ResponseEntity.ok(
								Map.of("students", student))));
	}

	public Mono<ResponseEntity<Map<StudentModel, List<CourseModel>>>> getStudentProfileById(Long studentId) {
		return studentRepo.findById(studentId)
				.flatMap(student -> enrollmentRepo.findByStudentId(studentId)
						.collectList()
						.flatMap(courses -> Mono.just(ResponseEntity.ok().body(Map.of(student, courses)))));
	}

	public Mono<ResponseEntity<Map<String, String>>> addChapter(ChapterModel chapter) {
		return courseRepo
				.findById(chapter.getCourseId()).flatMap(course -> {

					int chapterWeight = chapter.getWeight() * chapter.getPageLength();
					course.setWeight(course.getWeight() + chapterWeight);

					return chapterRepo
							.save(chapter)
							.flatMap(savedChapter -> Mono.just(
									ResponseEntity.ok(
											Map.of("message", "Chapter succesfully added to database"))));
				})
				.switchIfEmpty(Mono.just(
						ResponseEntity.status(HttpStatus.NOT_FOUND)
								.body(Map.of("message", "Can't add chapter no course found with that id"))));
	}

	public Mono<ResponseEntity<Map<String, String>>> enrollCourse(EnrollmentModel enrollment) {
		Mono<Boolean> studentExist = studentRepo.existsById(enrollment.getStudentId());
		Mono<Boolean> courseExist = courseRepo.existsById(enrollment.getCourseId());
		return Mono.zip(studentExist, courseExist)
				.flatMap(tuple -> {
					Boolean studentExists = tuple.getT1();
					Boolean courseExists = tuple.getT2();
					if (studentExists && courseExists) {
						return enrollmentRepo.save(enrollment)
								.flatMap(savedEnrolment -> Mono
										.just(ResponseEntity.ok().body(Map.of("message", "Successfully added students"))));
					} else {
						return Mono
								.just(ResponseEntity.badRequest().body(Map.of("message", "student id or course id  doesn't exist")));
					}
				});
	}
	// Helper method to check if two time blocks overlap

	private boolean isOverlapping(TimeBlockModel existingTimeBlock, TimeBlockModel newTimeBlock) {
		return (existingTimeBlock.getStartTime().before(newTimeBlock.getStartTime()) &&
				existingTimeBlock.getEndTime().after(newTimeBlock.getStartTime()))
				|| (existingTimeBlock.getStartTime().before(newTimeBlock.getEndTime()) &&
						existingTimeBlock.getEndTime().after(newTimeBlock.getEndTime()))
				|| (existingTimeBlock.getStartTime().equals(newTimeBlock.getStartTime()) &&
						existingTimeBlock.getEndTime().equals(newTimeBlock.getEndTime()));
	}

	public Mono<ResponseEntity<Map<String, String>>> createTimeBlock(TimeBlockModel newTimeBlock) {

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		boolean isFuture = newTimeBlock.getStartTime().after(currentTime);
		if (!isFuture) {
			return Mono.just(ResponseEntity.badRequest().body(Map.of("message", "startTime must be in the future")));
		}

		timeBlockRepo.findAllByOrderByStartTimeAsc()
				.filter(timeBlock -> isOverlapping(newTimeBlock, timeBlock))
				.next()
				.flatMap( // only if it has overlap
						overlappingTimeBlock -> {
							LocalDateTime prevStartTime = newTimeBlock.getStartTime().toLocalDateTime();
							LocalDateTime prevEndTime = newTimeBlock.getEndTime().toLocalDateTime();
							LocalDateTime overlappingStartTime = overlappingTimeBlock.getStartTime().toLocalDateTime();
							LocalDateTime endTime = overlappingTimeBlock.getEndTime().toLocalDateTime();

							Duration gap = Duration.ofMinutes(1); // 1 min gap
							Duration duration1 = Duration.between(prevStartTime, endTime);
							Duration duration = Duration.between(prevStartTime, prevEndTime);
							if (overlappingTimeBlock.getPriority() > newTimeBlock.getPriority()) { // TODO: refactor

								// newTimeBlock needs to be adjusted

								int compareValue = overlappingStartTime.compareTo(prevStartTime);
								if (compareValue <= 0) { // startTime before prevStartTime
									Timestamp newStartTime = Timestamp.valueOf(prevStartTime.plus(duration1).plus(gap));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration1));
									// shift the newTimeBlock
									newTimeBlock.setStartTime(newStartTime);
									newTimeBlock.setEndTime(newEndTime);
								} else { // startTime after prevStartTime
									Timestamp newStartTime = Timestamp.valueOf(prevEndTime.plus(gap));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration));
									// shift the newTimeBlock
									newTimeBlock.setStartTime(newStartTime);
									newTimeBlock.setEndTime(newEndTime);
								}
							} else {
								Duration durationOverlapping = Duration.between(overlappingStartTime, endTime);

								int compareValue = overlappingStartTime.compareTo(prevStartTime);
								if (compareValue <= 0) { // startTime before prevStartTime
									LocalDateTime newStartTime = overlappingStartTime;
									LocalDateTime newEndTime = overlappingStartTime.plus(duration);
									// shift the newTimeBlock
									newTimeBlock.setStartTime(Timestamp.valueOf(newStartTime));
									newTimeBlock.setEndTime(Timestamp.valueOf(newEndTime));
									// shift the overlapping timeblock
									LocalDateTime newOverlappingStartTime = newEndTime.plus(gap);
									LocalDateTime newOverlappingEndTime = newOverlappingStartTime.plus(durationOverlapping);

									overlappingTimeBlock.setStartTime(Timestamp.valueOf(newOverlappingStartTime));
									overlappingTimeBlock.setEndTime(Timestamp.valueOf(newOverlappingEndTime));

								} else { // startTime after prevStartTime
									// shift the overlapping timeblock
									LocalDateTime newOverlappingStartTime = prevEndTime.plus(gap);
									LocalDateTime newOverlappingEndTime = newOverlappingStartTime.plus(durationOverlapping);

									overlappingTimeBlock.setStartTime(Timestamp.valueOf(newOverlappingStartTime));
									overlappingTimeBlock.setEndTime(Timestamp.valueOf(newOverlappingEndTime));
								}

							}
							return Mono.just(overlappingTimeBlock);
						});
		return timeBlockRepo.save(newTimeBlock).flatMap(
				savedTimeBlock -> Mono.just(
						ResponseEntity.ok(
								Map.of("message", "Timeblock successfully added"))));

	}

}
