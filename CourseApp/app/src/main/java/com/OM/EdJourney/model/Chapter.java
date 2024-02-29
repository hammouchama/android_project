package com.OM.EdJourney.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter {

    @SerializedName("chapterId")
    @Expose
    private Long chapterId;

    @SerializedName("chapterName")
    @Expose
    private String chapterName;

    @SerializedName("videoID")
    @Expose
    private String videoID;

    @SerializedName("Description")
    @Expose
    private String description;


    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoUrl) {
        this.videoID = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
