package com.OM.EdJourney.ui.chapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.adapter.ChapterAdapter;
import com.OM.EdJourney.model.Chapter;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseChaptersList extends AppCompatActivity {
    RecyclerView chaptersRecyclerView ;
    ChapterAdapter chapterAdapter;
    ApiInterface apiInterface;
    Long courseId;

    String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chapters_list);
        chaptersRecyclerView = findViewById(R.id.chapters_list_recycler);


        //bar action
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        // Enable the back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left); // You can replace ic_arrow_back with your own drawable
            upArrow.setColorFilter(getResources().getColor(R.color.lavender), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            actionBar.setTitle("Chapters List");
        }




        // get courseId passed from previous activity
        Intent intent = getIntent(); // Use getIntent() to get the Intent that started this activity

        if (intent != null && intent.hasExtra("courseId")) {
            courseId = intent.getLongExtra("courseId", 0); // Provide a default value if "courseId" is not present
            courseName = intent.getStringExtra("courseName");
        } else {
            courseId = null;
            return; // You may want to handle this case appropriately
        }


        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<List<Chapter>> call = apiInterface.getCourseChapters(courseId);
        call.enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                List<Chapter> chapterList = response.body();

                // get completed chapters

                Call<List<Chapter>> callUserProgress = apiInterface.getCompletedChaptersByUserForCourse(1L, courseId);
                callUserProgress.enqueue(new Callback<List<Chapter>>() {
                    @Override
                    public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                        List<Chapter> completedChapterList = response.body();
                        // Assuming you want a vertical list, use LinearLayoutManager
                        /*getAllCategory(chapterListCompleted,courseId);*/
                        Log.d("Completed Chapters", "onResponse: "+completedChapterList);


                        List<Long> completedChapterIds = Collections.emptyList();

                        if(completedChapterList!= null) {
                            for (Chapter chapter: completedChapterList) {
                                completedChapterIds.add(chapter.getChapterId());
                            }
                        }



                        getAllCategory(chapterList,courseId,completedChapterIds);
                    }

                    @Override
                    public void onFailure(Call<List<Chapter>> call, Throwable t) {
                        Toast.makeText(CourseChaptersList.this, "Failed to fetch chapters", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(CourseChaptersList.this, "Failed to fetch chapters", Toast.LENGTH_SHORT).show();
            }
        });







    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle the new intent, update UI, or perform actions
    }

    private void getAllCategory(List<Chapter> chapterList, Long courseId, List<Long> completedChapterIds) {

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, 1);
        chaptersRecyclerView.setLayoutManager(layoutManager);
        chapterAdapter = new ChapterAdapter(this, chapterList,courseId,courseName,completedChapterIds);
        chaptersRecyclerView.setAdapter(chapterAdapter);
        chapterAdapter.notifyDataSetChanged();

    }
    // Handle back button click event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}