package net.oussa.backend.service;

import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Quiz;
import net.oussa.backend.model.User;
import net.oussa.backend.model.UserProgress;
import net.oussa.backend.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgressService {

    @Autowired
    private final UserProgressRepository userProgressRepository;

    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public ResponseEntity<?> addUserProgress(UserProgress userProgress) {
        try {
            // Implement logic to add user progress to the database
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


    // Add more methods as needed for updating, deleting, and other functionalities
}
