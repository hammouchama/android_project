package net.oussa.backend.controller;


import lombok.AllArgsConstructor;
import net.oussa.backend.model.UserProgress;
import net.oussa.backend.service.UserProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-progress")
@AllArgsConstructor
public class UserProgressController {

    private final UserProgressService userProgressService;

    @PostMapping("/add")
    public ResponseEntity<?> addUserProgress(@RequestBody UserProgress userProgress) {
        try {
            return userProgressService.addUserProgress(userProgress);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserProgress(@PathVariable long id) {
        try {
            return userProgressService.getUserProgress(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<?> getUserProgressByUser(@PathVariable long userId) {
        try {
            return userProgressService.getUserProgressByUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCompletedChapters/{userId}")
    public ResponseEntity<?> getCompletedChaptersByUser(@PathVariable long userId) {
        try {
            return userProgressService.getCompletedChaptersByUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCompletedChapters/{userId}/{courseId}")
    public ResponseEntity<?> getCompletedChaptersByUserForCourse(@PathVariable long userId, @RequestParam long courseId) {
        try {
            return userProgressService.getCompletedChaptersByUserForCourse(userId, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add more methods as needed for updating, deleting, and other functionalities
}