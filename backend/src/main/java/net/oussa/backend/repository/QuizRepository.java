package net.oussa.backend.repository;


import net.oussa.backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Custom query method to retrieve quizzes by chapter ID
    Quiz findByChapterChapterId(long chapterId);
}