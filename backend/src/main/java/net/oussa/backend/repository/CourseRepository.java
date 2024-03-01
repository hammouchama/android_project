package net.oussa.backend.repository;

import net.oussa.backend.mappers.CourseDTO;
import net.oussa.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Query(value = "SELECT co.course_id,co.course_name, co.course_description,  co.image, co.stars, co.teacher,co.level, COUNT(c.chapter_id) AS nb_chapter " +
            "FROM courses co " +
            "LEFT JOIN chapters c ON co.course_id = c.course_id " +
            "GROUP BY co.course_id",
            nativeQuery = true)
    List<Object[]> getAllCourse();
}
