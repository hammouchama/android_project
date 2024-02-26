package net.oussa.backend.controller;


import lombok.AllArgsConstructor;
import net.oussa.backend.model.Question;
import net.oussa.backend.model.Quiz;
import net.oussa.backend.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/add/{chapterId}")
    public ResponseEntity<?> addQuiz(@RequestBody Quiz quiz,@PathVariable long chapterId) {
        try {
            return quizService.addQuiz(quiz,chapterId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable long id) {
        try {
            return quizService.getQuiz(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getByChapter/{chapterId}")
    public ResponseEntity<?> getQuizByChapter(@PathVariable long chapterId) {
        try {
            return quizService.getQuizByChapter(chapterId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz, @PathVariable long id) {
        try {
            return quizService.updateQuiz(id, quiz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable long id) {
        try {
            return quizService.deleteQuiz(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        try {
            return quizService.getAllQuizzes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Questions
     * */
    // add question or more
    @PostMapping("/addQuestion/{quizId}")
    public ResponseEntity<?> addQuestion(@RequestBody Question question, @PathVariable long quizId) {
        try {
            return quizService.addQuestion(quizId, question);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}