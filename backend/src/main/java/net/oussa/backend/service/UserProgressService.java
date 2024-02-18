package net.oussa.backend.service;

import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Quiz;
import net.oussa.backend.model.User;
import net.oussa.backend.model.UserProgress;
import net.oussa.backend.repository.UserProgressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProgressService {

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
            List<UserProgress> userProgressList = userProgressRepository.findByUser_IdAndIsCompletedTrue(userId);
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

    public ResponseEntity<?> getCompletedQuizzesByUser(long userId) {
        try {
            // Implement logic to retrieve completed quizzes by user ID from the database
            List<UserProgress> userProgressList = userProgressRepository.findByUser_IdAndIsCompletedTrue(userId);
            List<Quiz> completedQuizzes = userProgressList.stream()
                    .filter(userProgress -> userProgress.getQuiz() != null)
                    .map(UserProgress::getQuiz)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(completedQuizzes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve completed quizzes by user");
        }
    }

    public ResponseEntity<?> updateProgress(User user, Chapter chapter, Quiz quiz, boolean isCompleted) {
        try {
            // Implement logic to update user progress based on completion of chapter or quiz
            Optional<UserProgress> userProgressOptional = userProgressRepository.findByUserAndChapterAndQuiz(user, chapter, quiz);
            if (userProgressOptional.isPresent()) {
                UserProgress userProgress = userProgressOptional.get();
                userProgress.setIsCompleted(isCompleted);
                userProgressRepository.save(userProgress);
            } else {
                UserProgress newUserProgress = new UserProgress();
                newUserProgress.setUser(user);
                newUserProgress.setChapter(chapter);
                newUserProgress.setQuiz(quiz);
                newUserProgress.setIsCompleted(isCompleted);
                userProgressRepository.save(newUserProgress);
            }
            return ResponseEntity.ok("User progress updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update user progress");
        }
    }

    // Add more methods as needed for updating, deleting, and other functionalities
}
