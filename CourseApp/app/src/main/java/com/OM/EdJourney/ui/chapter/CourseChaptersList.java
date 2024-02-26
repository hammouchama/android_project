package com.OM.EdJourney.ui.chapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.OM.EdJourney.QuizActivity;
import com.OM.EdJourney.R;
import com.OM.EdJourney.adapter.ChapterAdapter;
import com.OM.EdJourney.model.Chapter;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseChaptersList extends AppCompatActivity {
    RecyclerView chaptersRecyclerView ;
    ChapterAdapter chapterAdapter;
    ApiInterface apiInterface;
    Long courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chapters_list);
        chaptersRecyclerView = findViewById(R.id.chapters_list_recycler);
        // get courseId passed from previous activity
        Intent intent = getIntent(); // Use getIntent() to get the Intent that started this activity

        if (intent != null && intent.hasExtra("courseId")) {
            courseId = intent.getLongExtra("courseId", 0); // Provide a default value if "courseId" is not present
            Log.i("course id ", String.valueOf(courseId));
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
                // Assuming you want a vertical list, use LinearLayoutManager
                getAllCategory(chapterList);
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(CourseChaptersList.this, "Failed to fetch chapters", Toast.LENGTH_SHORT).show();
            }
        });

        // add onclick listner on the id imageView10
        /*TEST*/
        ImageView imageView10 = findViewById(R.id.imageView10);
        Long chapterId = 1L;
        imageView10.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, QuizActivity.class);
            intent1.putExtra("CHAPTER_ID", chapterId);
            startActivity(intent1);
        });




    }
    private void getAllCategory(List<Chapter> chapterList) {

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, 1);
        chaptersRecyclerView.setLayoutManager(layoutManager);
        chapterAdapter = new ChapterAdapter(this, chapterList);
        chaptersRecyclerView.setAdapter(chapterAdapter);
        chapterAdapter.notifyDataSetChanged();

    }

}