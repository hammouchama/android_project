package net.oussa.backend.repository;

import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    // Custom query to find a chapter by name
    Chapter findByChapterName(String chapterName);

    // Custom query to find chapters by course
    List<Chapter> findChapterByCourse(Course course);
}