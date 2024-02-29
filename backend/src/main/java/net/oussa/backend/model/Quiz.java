package net.oussa.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quizzes")
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
public class Quiz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(name = "number_of_questions")
    private int numberOfQuestions = 5;

    @Column(name = "time_per_question")
    private int timePerQuestion = 30; // in seconds

    @Column(name = "required_score")
    private int requiredScore = 80; // in percent

    @OneToMany(mappedBy = "quiz", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Question> questions;

}
