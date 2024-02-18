package net.oussa.backend.controller;

import lombok.AllArgsConstructor;
import net.oussa.backend.model.Course;
import net.oussa.backend.service.CourseService;
import net.oussa.backend.util.Helper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
@CrossOrigin("*")
public class CourseController {
    CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestPart("image") MultipartFile image, @RequestPart("course") Course course){
        try {

            return courseService.addCourse(image,course);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCourse(@PathVariable long id){
        try {
            return courseService.getCourse(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/get")
    public ResponseEntity<?> getAllCourses(){
        try {
            return courseService.getAllCourses();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@RequestPart("image") MultipartFile image, @RequestPart("course") Course course,@PathVariable long id){
        try {
            return courseService.updateCourse(id,course,image);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id){
        try {
            return courseService.deleteCourse(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @CrossOrigin("*")
    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id) {
        String path = "src/main/resources/static/images/" + id;
        FileSystemResource file = new FileSystemResource(path);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(file);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving the image.");
        }
    }

}
