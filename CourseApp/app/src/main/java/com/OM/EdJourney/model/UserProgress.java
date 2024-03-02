package com.OM.EdJourney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProgress {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("courseId")
    @Expose
    private Long courseId;


    @SerializedName("chapterId")
    @Expose
    private Long chapterId;


    public UserProgress(Long userId, Long courseId,Long chapterId) {
        this.userId  = userId;
        this.courseId   = courseId;
        this.chapterId  = chapterId;
    }
    @Override
    public String toString() {
        return "UserProgress{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", chapterIdId=" + chapterId +
                '}';
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
