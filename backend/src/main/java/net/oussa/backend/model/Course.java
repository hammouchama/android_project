package net.oussa.backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false,columnDefinition = "TEXT",length = 5000)
    private String image;
}
