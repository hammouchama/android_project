
package com.OM.EdJourney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("courseId")
    @Expose
    private Long courseId;
    @SerializedName("courseDescription")
    @Expose
    private String courseDescription;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("teacher")
    @Expose
    private String teacher;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public float getNb_chapter() {
        return nb_chapter;
    }

    public void setNb_chapter(float nb_chapter) {
        this.nb_chapter = nb_chapter;
    }

    @SerializedName("stars")
    @Expose
    private float stars;

    @SerializedName("nb_chapter")
    @Expose
    private float nb_chapter;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
