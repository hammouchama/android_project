package net.hammouchama.e_learnebackend.repository;

import net.hammouchama.e_learnebackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course ,Long> {
}
