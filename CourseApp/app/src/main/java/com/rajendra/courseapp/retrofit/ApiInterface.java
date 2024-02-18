package com.rajendra.courseapp.retrofit;

import com.rajendra.courseapp.model.Category;
import com.rajendra.courseapp.model.Chapter;
import com.rajendra.courseapp.model.Course;
import com.rajendra.courseapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("course/get")
    Call<List<Course>> getAllCourses();

    @GET("course")
    Call<List<Course>> getCourseContent();
    @POST("user/login")
    Call<User> loginUser(@Body User user);
    @POST("user/register")
    Call<Void> registerUser(@Body User user);

    // Get the list of chapters of a course with {courseId}
    @GET("course/{courseId}/chapters")
    Call<List<Chapter>> getCourseChapters(Long courseId);

    // we need to make model class for our data
    // first have a look on json structure.

}
