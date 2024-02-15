package net.oussa.backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "is_locked", columnDefinition = "INTEGER DEFAULT 0")
    private int isLocked;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String Description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}