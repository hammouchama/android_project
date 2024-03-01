package net.oussa.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chapters")
@Getter @Setter @Builder @ToString @AllArgsConstructor @NoArgsConstructor
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long chapterId;


    @Column(name = "chapter_name", nullable = false)
    private String chapterName;

    @Column(name = "video_url")
    private String videoID ;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

    //one quiz
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    private String estimated_minute="0";
}