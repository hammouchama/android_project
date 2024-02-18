package net.oussa.backend.service;


import lombok.AllArgsConstructor;
import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Course;
import net.oussa.backend.repository.ChapterRepository;
import net.oussa.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChapterService {

    private  ChapterRepository chapterRepository;
    private CourseRepository courseRepository;


    public ResponseEntity<?> addChapter(Chapter chapter, long courseId) {
        // Add logic for validating and saving the chapter to the database
        try {
            Optional<Course> course = courseRepository.findById(courseId);
            if (course.isPresent()){
                chapter.setCourse(course.get());
                return  new ResponseEntity<>(chapterRepository.save(chapter),HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Course not found",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("something was wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Chapter getChapter(long id) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);
        return optionalChapter.orElse(null);
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public Chapter updateChapter(long id, Chapter updatedChapter) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(id);

        if (optionalChapter.isPresent()) {
            Chapter existingChapter = optionalChapter.get();
            // Update the existing chapter with the values from updatedChapter
            existingChapter.setChapterName(updatedChapter.getChapterName());
            existingChapter.setDescription(updatedChapter.getDescription());

            // Save the updated chapter to the database
            return chapterRepository.save(existingChapter);
        } else {
            // Handle the case where the chapter with the given id is not found
            return null;
        }
    }

    public void deleteChapter(long id) {
        chapterRepository.deleteById(id);
    }

    public ResponseEntity<?> getChapterByCourseId(long id) {
        try {
            Optional<Course> courseById = courseRepository.findById(id);
            if (courseById.isPresent()){
                return new ResponseEntity<>(chapterRepository.findChapterByCourse(courseById.get()),HttpStatus.OK);
            }
            return new ResponseEntity<>("course not found",HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>("something was wrong ",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}