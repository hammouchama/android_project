package net.oussa.backend.service;

import lombok.AllArgsConstructor;
import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Question;
import net.oussa.backend.model.Quiz;
import net.oussa.backend.repository.ChapterRepository;
import net.oussa.backend.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    private  QuizRepository quizRepository;
    private ChapterRepository chapterRepository;
    public ResponseEntity<?> addQuiz(Quiz quiz, long chapterId) {
        try {
            Optional<Chapter> chapter = chapterRepository.findById(chapterId);
            if (chapter.isPresent()){
                quiz.setChapter(chapter.get());
                // add this quiz id to each question
                List<Question> questions = quiz.getQuestions();
                for (Question question : questions) {
                    question.setQuiz(quiz);
                }

                quizRepository.save(quiz);
                return ResponseEntity.ok("Quiz added successfully");
            }
            return  new ResponseEntity<>("Chapter not found", HttpStatus.BAD_REQUEST);

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

    public ResponseEntity<?> getQuizByChapter(long chapterId) {
        try {
            Optional<Chapter> chapter=chapterRepository.findById(chapterId);
            System.out.println(chapter.get());
            Optional<Quiz> quiz = quizRepository.findQuizByChapter(chapter.get());
          return  new ResponseEntity<>(quiz.get(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to retrieve quiz by chapter");
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

    /**
     * Questions
     * */
    public ResponseEntity<?> addQuestion(long quizId, Question question) {
        try {
            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                List<Question> questions = quiz.getQuestions();
                questions.add(question);
                quiz.setQuestions(questions);
                quizRepository.save(quiz);
                return ResponseEntity.ok("Question added successfully");
            } else {
                return ResponseEntity.status(404).body("Quiz not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add question");
        }
    }
}
