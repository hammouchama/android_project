package net.oussa.backend.service;

import net.oussa.backend.model.Quiz;
import net.oussa.backend.repository.QuizRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public ResponseEntity<?> addQuiz(Quiz quiz) {
        try {
            // Implement logic to add a new quiz to the database
            quizRepository.save(quiz);
            return ResponseEntity.ok("Quiz added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add quiz");
        }
    }

    public ResponseEntity<?> getQuiz(long id) {
        try {
            // Implement logic to retrieve a quiz by ID from the database
            Optional<Quiz> quizOptional = quizRepository.findById(id);
            if(quizOptional.isPresent()){
                return ResponseEntity.ok(quizOptional.get());
            }else{
                return ResponseEntity.status(404).body("Quiz not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve quiz");
        }
    }

    public ResponseEntity<?> getQuizzesByChapter(long chapterId) {
        try {
            // Implement logic to retrieve quizzes by chapter ID from the database
            List<Quiz> quizzes = quizRepository.findByChapterChapterId(chapterId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve quizzes by chapter");
        }
    }

    public ResponseEntity<?> updateQuiz(long id, Quiz quiz) {
        try {
            // Implement logic to update a quiz by ID in the database
            Optional<Quiz> existingQuizOptional = quizRepository.findById(id);
            if (existingQuizOptional.isPresent()) {
                Quiz existingQuiz = existingQuizOptional.get();
                // Update quiz fields as needed
                existingQuiz.setChapter(quiz.getChapter());
                existingQuiz.setQuestions(quiz.getQuestions());
                // Save the updated quiz
                quizRepository.save(existingQuiz);
                return ResponseEntity.ok("Quiz updated successfully");
            } else {
                return ResponseEntity.status(404).body("Quiz not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update quiz");
        }
    }

    public ResponseEntity<?> deleteQuiz(long id) {
        try {
            // Implement logic to delete a quiz by ID from the database
            quizRepository.deleteById(id);
            return ResponseEntity.ok("Quiz deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to delete quiz");
        }
    }

    public ResponseEntity<?> getAllQuizzes() {
        try {
            // Implement logic to retrieve all quizzes from the database
            List<Quiz> quizzes = quizRepository.findAll();
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve quizzes");
        }
    }
}
