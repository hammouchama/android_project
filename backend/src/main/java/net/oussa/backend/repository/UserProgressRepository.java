package net.oussa.backend.repository;

import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Quiz;
import net.oussa.backend.model.User;
import net.oussa.backend.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    List<UserProgress> findByUser_Id(long userId);

    List<UserProgress> findByUser_IdAndIsCompletedTrue(long userId);

    Optional<UserProgress> findByUserAndChapterAndQuiz(User user, Chapter chapter, Quiz quiz);
}
