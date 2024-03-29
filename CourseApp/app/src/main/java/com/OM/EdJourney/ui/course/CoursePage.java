package com.OM.EdJourney.ui.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.OM.EdJourney.R;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

public class CoursePage extends AppCompatActivity {

    RecyclerView courseRecyclerView;
    ApiInterface apiInterface;
   // CourseAdapter courseAdapter;

    TextView member, rating, name, price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_course_page);

        member = findViewById(R.id.members);
        rating = findViewById(R.id.rating);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        courseRecyclerView = findViewById(R.id.course_recycler);

        //Call<List<Course>> call = apiInterface.getCourseContent();
  /*
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {

                List<Course> courseList = response.body();

                getCourseContent(courseList.get(0).getPlayList());
                //lets run it


                member.setText(courseList.get(0).getMember());
                rating.setText(courseList.get(0).getRating());
                name.setText(courseList.get(0).getCourseName());
                price.setText("₹ "+courseList.get(0).getPrice());

            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(CoursePage.this, "No response from server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCourseContent(List<PlayList> playLists){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        courseRecyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(this, playLists);
        courseRecyclerView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();



    }

   */

        // on click on the button id/textView6 it should go to the next activity which is CourseChaptersList activity
        TextView button = findViewById(R.id.textView6);
        button.setOnClickListener(v -> {
            // start the CourseChaptersList activity and pass the courseId to it, the courseId depends on the course clicked
            Intent intent = new Intent(CoursePage.this, CourseChaptersList.class);
            intent.putExtra("courseId",1l);
            startActivity(intent);
        });


    }
}
