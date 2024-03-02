package com.OM.EdJourney.model;

public class UserProgress {

    private Long id;

    private Long userId;
    private Long courseId;

    private Long chapterId;


    public UserProgress(Long userId, Long courseId,Long chapterId) {
        this.userId = userId;
        this.courseId = courseId;
        this.chapterId = chapterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
