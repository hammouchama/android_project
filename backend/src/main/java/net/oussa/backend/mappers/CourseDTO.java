package net.oussa.backend.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CourseDTO {

    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String image;
    private String teacher;
    private float stars;
    private Long nb_chapter;

    public CourseDTO(Long courseId, String courseDescription, String courseName, String image, float stars, String teacher, Long nb_chapter) {
        this.courseId = courseId;
        this.courseDescription = courseDescription;
        this.courseName = courseName;
        this.image = image;
        this.stars = stars;
        this.teacher = teacher;
        this.nb_chapter = nb_chapter;
    }
    // Constructor for converting array to CourseDTO
    public CourseDTO(Object[] array) {
        this.courseId = (Long) array[0];
        this.courseName = (String) array[1];
        this.courseDescription = (String) array[2];
        this.image = (String) array[3];
        this.stars = (Float) array[4];
        this.teacher = (String) array[5];
        this.nb_chapter = (Long) array[6];
    }
}
