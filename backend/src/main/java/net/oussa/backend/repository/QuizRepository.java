package net.oussa.backend.repository;


import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Custom query method to retrieve quizzes by chapter ID
    Optional<Quiz> findQuizByChapter(Chapter chapterId);
}