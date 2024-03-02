package net.oussa.backend.service;

import net.oussa.backend.dto.UserProgressDTO;
import net.oussa.backend.model.*;
import net.oussa.backend.repository.ChapterRepository;
import net.oussa.backend.repository.CourseRepository;
import net.oussa.backend.repository.UserProgressRepository;
import net.oussa.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgressService {

    @Autowired
    private UserProgressRepository userProgressRepository;
    //UserRepository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseRepository courseRepository;





    public UserProgressService() {
    }

    public ResponseEntity<?> addUserProgress(UserProgressDTO userProgressDto) {
        try {
            System.out.println(userProgressDto);
            UserProgress userProgress = new UserProgress();
            User user = userRepository.findById(userProgressDto.getUserId()).get();
            userProgress.setUser(user);
            Course course = courseRepository.findById(userProgressDto.getCourseId()).get();
            userProgress.setCourse(course);
            Chapter chapter = chapterRepository.findById(userProgressDto.getChapterId()).get();
            userProgress.setChapter(chapter);


            userProgressRepository.save(userProgress);
            return ResponseEntity.ok("User progress added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add user progress");
        }
    }

    public ResponseEntity<?> getUserProgress(long id) {
        try {
            // Implement logic to retrieve user progress by ID from the database
            Optional<UserProgress> userProgressOptional = userProgressRepository.findById(id);

            if (userProgressOptional.isPresent()) {
                return ResponseEntity.ok(userProgressOptional.get());
            } else {
                return ResponseEntity.status(404).body("User progress not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve user progress");
        }
    }

    public ResponseEntity<?> getUserProgressByUser(long userId) {
        try {
            // Implement logic to retrieve user progress by user ID from the database
            List<UserProgress> userProgressList = userProgressRepository.findByUser_Id(userId);
            return ResponseEntity.ok(userProgressList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve user progress by user");
        }
    }

    public ResponseEntity<?> getCompletedChaptersByUser(long userId) {
        try {
            // Implement logic to retrieve completed chapters by user ID from the database
            List<UserProgress> userProgressList = userProgressRepository.findByUser_Id(userId);
            List<Chapter> completedChapters = userProgressList.stream()
                    .filter(userProgress -> userProgress.getChapter() != null)
                    .map(UserProgress::getChapter)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(completedChapters);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve completed chapters by user");
        }
    }

    // getCompletedChaptersByUserForCourse
    public ResponseEntity<?> getCompletedChaptersByUserForCourse(long userId, long courseId) {
        try {
            // Implement logic to retrieve completed chapters by user ID and course ID from the database
            List<UserProgress> userProgressList = userProgressRepository.findByUser_IdAndChapter_Course_CourseId(userId, courseId);
            List<Chapter> completedChapters = userProgressList.stream()
                    .filter(userProgress -> userProgress.getChapter() != null)
                    .map(UserProgress::getChapter)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(completedChapters);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve completed chapters by user for course");
        }
    }


    // get count of all chapters by userId
    public ResponseEntity<?> getChaptersCountByUser(long userId) {
        try {
            // Implement logic to retrieve count of all chapters by user ID from the database
            List<UserProgress> userProgressList = userProgressRepository.findByUser_Id(userId);
            long chaptersCount = userProgressList.stream()
                    .filter(userProgress -> userProgress.getChapter() != null)
                    .count();
            return ResponseEntity.ok(chaptersCount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve count of all chapters by user");
        }
    }


    // Add more methods as needed for updating, deleting, and other functionalities
}
