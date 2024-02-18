package net.oussa.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quizzes")
@Getter @Setter @ToString @Builder @AllArgsConstructor @NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions=new ArrayList<>();

}
