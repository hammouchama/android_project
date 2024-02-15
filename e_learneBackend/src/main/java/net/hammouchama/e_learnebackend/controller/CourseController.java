package net.hammouchama.e_learnebackend.controller;

import net.hammouchama.e_learnebackend.model.Course;
import net.hammouchama.e_learnebackend.model.PlayList;
import net.hammouchama.e_learnebackend.repository.CourseRepository;
import net.hammouchama.e_learnebackend.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private PlayListRepository playListRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
     Course course1=courseRepository.save(course);
        PlayList ply=new PlayList(1L,"chiihaja","30h",course);
        playListRepository.save(ply);

        return course1;
    }

}