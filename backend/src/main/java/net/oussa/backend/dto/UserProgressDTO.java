package net.oussa.backend.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.oussa.backend.model.Chapter;
import net.oussa.backend.model.Course;
import net.oussa.backend.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressDTO {

    private Long userId;


    private Long courseId;

    private Long chapterId;

    @Override
    public String toString() {
        return "UserProgressDTO{" +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", chapterId=" + chapterId +
                '}';
    }
}
