package com.OM.EdJourney.retrofit;

import com.OM.EdJourney.model.Chapter;
import com.OM.EdJourney.model.Course;
import com.OM.EdJourney.model.Question;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("course/get")
    Call<List<Course>> getAllCourses();

    @POST("user/login")
    Call<User> loginUser(@Body User user);
    @POST("user/register")
    Call<Void> registerUser(@Body User user);

    // Get the list of chapters of a course with {courseId}
    @GET("course/{id}/chapters")
    Call<List<Chapter>> getCourseChapters(@Path("id")Long id);

    @GET("quiz/getByChapter/{chapterId}")
    Call<Quiz> getQuizByChapter(@Path("chapterId")Long chapterId);

}
