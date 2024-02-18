package com.rajendra.courseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.rajendra.courseapp.adapter.ChapterAdapter;
import com.rajendra.courseapp.model.Chapter;
import com.rajendra.courseapp.model.Course;
import com.rajendra.courseapp.model.PlayList;
import com.rajendra.courseapp.retrofit.ApiInterface;
import com.rajendra.courseapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseChaptersList extends AppCompatActivity {
    RecyclerView chaptersRecyclerView ;
    ApiInterface apiInterface;
    Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chapters_list);
        chaptersRecyclerView = findViewById(R.id.chapters_list_recycler);
        // get courseId passed from previous activity
        if(!getIntent().getExtras().isEmpty()){
            courseId = getIntent().getExtras().getLong("courseId");
        }else{
            courseId=null;
            return;
        }
        Log.i("course id ",courseId+"");

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<List<Chapter>> call = apiInterface.getCourseChapters();
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                List<Chapter> chapterList = response.body();
                ChapterAdapter chapterAdapter = new ChapterAdapter(CourseChaptersList.this, chapterList);
                chaptersRecyclerView.setAdapter(chapterAdapter);
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(CourseChaptersList.this, "Failed to fetch chapters", Toast.LENGTH_SHORT).show();
            }
        });


    }
}