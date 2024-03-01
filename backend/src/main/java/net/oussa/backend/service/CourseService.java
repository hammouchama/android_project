package net.oussa.backend.service;

import lombok.AllArgsConstructor;
import net.oussa.backend.mappers.CourseDTO;
import net.oussa.backend.model.Course;
import net.oussa.backend.repository.CourseRepository;
import net.oussa.backend.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service

public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Value("${server.address}")
    private String serverAddress;
    public ResponseEntity<?> addCourse(MultipartFile image, Course course) {
        try {
            course.setImage(Helper.saveImage(image,serverAddress));
            return new ResponseEntity<>(courseRepository.save(course),HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getCourse(long id) {
        try {
            Optional<Course> course =courseRepository.findById(id);
            if (course.isPresent()){
                return new ResponseEntity<>(course.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>("Course not fond",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getAllCourses() {
        try {
            List<Object[]> courses=courseRepository.getAllCourse();
            List<CourseDTO> result=new ArrayList<>();
            for (var course:courses) {
                result.add(new CourseDTO(course));
            }
            System.out.println(courses.get(0));
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<?> updateCourse(long id, Course course, MultipartFile image) {
        try {
            Optional<Course> old_course =courseRepository.findById(id);
            if (old_course.isPresent()){
                Course newCourse=old_course.get();
                newCourse.setCourseId(id);
                newCourse.setCourseDescription(course.getCourseDescription());
                newCourse.setCourseName(course.getCourseName());
                newCourse.setStars(course.getStars());
                newCourse.setTeacher(course.getTeacher());
                newCourse.setLevel(course.getLevel());
                if(!image.isEmpty()){
                    newCourse.setImage(Helper.saveImage(image,serverAddress));
                }
                return new ResponseEntity<>(courseRepository.save(newCourse),HttpStatus.OK);
            }
            return new ResponseEntity<>("Course not fond",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<?> deleteCourse(long id) {
        try {
            Optional<Course> course =courseRepository.findById(id);
            if (course.isPresent()){
                courseRepository.delete(course.get());
                return new ResponseEntity<>("Course deleted successfully",HttpStatus.OK);
            }
            return new ResponseEntity<>("Course not fond",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
