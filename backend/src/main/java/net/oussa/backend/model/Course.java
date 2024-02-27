package net.oussa.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@NamedQuery(name = "Course.getAllCourse",query = "SELECT new net.oussa.backend.mappers.CourseDTO(co.courseId, co.courseDescription, co.courseName, co.image,co.stars,co.teacher , COUNT(c.chapterId) ) FROM Course AS co LEFT JOIN Chapter AS c ON co.courseId = c.chapterId GROUP BY co.courseId")
@Entity
@Table(name = "Courses")
@Getter @Setter @Builder @ToString @AllArgsConstructor @NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "course_description",columnDefinition = "TEXT",length = 5000,nullable = false)
    private String courseDescription;


    private String image;

    private String teacher;

    private float stars;


}
