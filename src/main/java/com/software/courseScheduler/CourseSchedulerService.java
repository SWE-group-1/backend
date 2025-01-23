package com.software.courseScheduler;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.software.courseScheduler.Course.*;
import com.software.courseScheduler.DeadLine.*;
import com.software.courseScheduler.TimeBlock.*;

import reactor.core.publisher.Mono;

@Service
public class CourseSchedulerService {
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private ChapterRepo chapterRepo;
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

	public Mono<ResponseEntity<Map<String, String>>> addChapter(ChapterModel chapter) {
		return courseRepo
				.findById(chapter.getCouserId()).flatMap(course -> {

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
						overlapingTimeBlock -> {
							LocalDateTime prevStartTime = newTimeBlock.getStartTime().toLocalDateTime();
							LocalDateTime prevEndTime = newTimeBlock.getEndTime().toLocalDateTime();
							LocalDateTime startTime = overlapingTimeBlock.getStartTime().toLocalDateTime();
							LocalDateTime endTime = overlapingTimeBlock.getEndTime().toLocalDateTime();

							Duration gap = Duration.ofMinutes(1); // 1 min gap
							if (overlapingTimeBlock.getPriority() > newTimeBlock.getPriority()) { // TODO: refactor

								Duration duration1 = Duration.between(prevStartTime, endTime);
								Duration duration2 = Duration.between(prevEndTime, endTime);
								Duration duration = Duration.between(prevStartTime, prevEndTime);
								// newTimeBlock needs to be adjusted

								int compareValue = duration1.compareTo(duration2);
								if (compareValue <= 0) { // duration1 min
									Timestamp newStartTime = Timestamp.valueOf(prevStartTime.plus(duration1).plus(gap));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration1).plus(gap));
									// shift the newTimeBlock
									newTimeBlock.setStartTime(newStartTime);
									newTimeBlock.setEndTime(newEndTime);
								} else { // duration2 min
									Timestamp newStartTime = Timestamp.valueOf(prevStartTime.plus(duration2).plus(duration));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration2).plus(duration));
									// shift the newTimeBlock
									newTimeBlock.setStartTime(newStartTime);
									newTimeBlock.setEndTime(newEndTime);
								}
							} else {
								// overlapingTimeBlock needs to be adjusted
								Duration duration1 = Duration.between(startTime, prevEndTime);
								Duration duration2 = Duration.between(prevEndTime, endTime);
								Duration duration = Duration.between(startTime, endTime);

								int compareValue = duration1.compareTo(duration2);
								if (compareValue <= 0) { // duration1 min
									Timestamp newStartTime = Timestamp.valueOf(prevStartTime.plus(duration1).plus(gap));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration1).plus(gap));
									// shift the newTimeBlock
									overlapingTimeBlock.setStartTime(newStartTime);
									overlapingTimeBlock.setEndTime(newEndTime);
								} else { // duration2 min
									Timestamp newStartTime = Timestamp.valueOf(prevStartTime.plus(duration2).plus(duration));
									Timestamp newEndTime = Timestamp.valueOf(prevEndTime.plus(duration2).plus(duration));
									// shift the newTimeBlock
									overlapingTimeBlock.setStartTime(newStartTime);
									overlapingTimeBlock.setEndTime(newEndTime);
								}

							}
							return Mono.just(overlapingTimeBlock);
						});
		return timeBlockRepo.save(newTimeBlock).flatMap(
				savedTimeBlock -> Mono.just(
						ResponseEntity.ok(
								Map.of("message", "Timeblock successfully added"))));

	}

	public Mono<ResponseEntity<Map<String, String>>> createDeadline(DeadlineModel deadline) { // taking deadline as just
																																														// incomming exam
		//
		// calculate how many studyHour is the deadline consider the time(if is it close
		// or not) consider the weight of the deadline(compulative weight of the
		// chapters weight)
		// create a time blocks from current time to deadlineTime
	}
}
