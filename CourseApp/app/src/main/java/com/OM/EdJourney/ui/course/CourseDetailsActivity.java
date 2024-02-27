package com.OM.EdJourney.ui.course;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.OM.EdJourney.R;
import com.bumptech.glide.Glide;

import java.util.Objects;


public class CourseDetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView description,title;

    Button start;

    ActionBar actionBar;
    private static Long courseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         actionBar = getSupportActionBar();

        // Enable the back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left); // You can replace ic_arrow_back with your own drawable
            upArrow.setColorFilter(getResources().getColor(R.color.lavender), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        //load the content
        loadData();

        start.setOnClickListener(v -> {
            Intent intent = new Intent(CourseDetailsActivity.this, CourseChaptersList.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        });

    }

    // Handle back button click event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void loadData() {
        image = findViewById(R.id.image);
        title = findViewById(R.id.course_title);
        description = findViewById(R.id.description);

        start = findViewById(R.id.start_course);
        Intent intent = getIntent();

        title.setText("Welcome To " + Objects.requireNonNull(intent.getStringExtra("courseName")) +" course");
        description.setText(Objects.requireNonNull(intent.getStringExtra("courseDescription")));

        String courseIdString = intent.getStringExtra("courseId");
        if (courseIdString != null) {
            try {
                courseId = Long.parseLong(courseIdString);
            } catch (NumberFormatException e) {
                // Handle the case where parsing fails
                e.printStackTrace(); // Log the exception for debugging
            }
        } else {
            // Handle the case where courseIdString is null
        }

        Glide.with(this)
                .load(intent.getStringExtra("courseImage"))
                .centerCrop()
                .into(image);

        actionBar.setTitle(Objects.requireNonNull(intent.getStringExtra("courseName")));
    }


}