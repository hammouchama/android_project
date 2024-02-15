package com.rajendra.courseapp.retrofit;

import com.rajendra.courseapp.model.Category;
import com.rajendra.courseapp.model.Course;
import com.rajendra.courseapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("category")
    Call<List<Category>> getAllCategory();

    @GET("course")
    Call<List<Course>> getCourseContent();
    @POST("user/login")
    Call<User> loginUser(@Body User user);
    @POST("user/register")
    Call<Void> registerUser(@Body User user);

    // we need to make model class for our data
    // first have a look on json structure.

}
