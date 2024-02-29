package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreQuizActivity extends AppCompatActivity {
    String chapterName, courseName;
    Long courseId, chapterId,contentNumber;

    ActionBar actionBar;

    Quiz quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_quiz);

        // handle toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        // Enable the back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            // take off the title and the shadow and border
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(0);




            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left); // You can replace ic_arrow_back with your own drawable
            upArrow.setColorFilter(getResources().getColor(R.color.lavender), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);


        }




        chapterName = getIntent().getStringExtra("chapterName");
        contentNumber = getIntent().getLongExtra("contentNumber",1);
        courseId = getIntent().getLongExtra("courseId", 0);
        chapterId = getIntent().getLongExtra("chapterId", 0);
        courseName = getIntent().getStringExtra("courseName");


        // put courseName
        TextView courseNameView = findViewById(R.id.course_name);
        courseNameView.setText(courseName+": Quiz"+getTwoDigitsNumber(contentNumber));

        // put contentNumber
        TextView contentNumberView = findViewById(R.id.content_number);
        contentNumberView.setText(getTwoDigitsNumber(contentNumber));

        // put chapter_name
        TextView chapterNameView = findViewById(R.id.chapter_name);
        chapterNameView.setText(chapterName);

        // fetch quiz details
        fetchQuizDetails(chapterId);

    }

    // Have a function that gets a long number and returns in string the number in two digits instead of one only by adding 0 in case < 10
    public String getTwoDigitsNumber(long number){
        if(number<10){
            return "0"+number;
        }
        return String.valueOf(number);
    }

    // Handle back button click event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // handle after fetching data
    private void handleFetchedData() {

        if(quiz== null){
            Toast.makeText(this, "Failed to fetch quiz details : err 3", Toast.LENGTH_SHORT).show();
            return;
        }
        int numberOfQuestions = quiz.getNumberOfQuestions();
        int timePerQuestion = quiz.getTimePerQuestion();
        int requiredScore = quiz.getRequiredScore();

        // put numberOfQuestions in number_of_questions
        TextView numberOfQuestionsView = findViewById(R.id.infos_item_1_title);
        numberOfQuestionsView.setText(String.valueOf(numberOfQuestions));

        // put timePerQuestion in time_per_question
        TextView timePerQuestionView = findViewById(R.id.infos_item_2_title);
        timePerQuestionView.setText(String.valueOf(timePerQuestion)+" seconds");

        // put requiredScore in required_score
        TextView requiredScoreView = findViewById(R.id.infos_item_3_title);
        requiredScoreView.setText(String.valueOf(requiredScore)+"%");



        // add listner to start_quiz button
        findViewById(R.id.start_quiz).setOnClickListener(view -> {
            // start QuizActivity
            Intent intent = new Intent(PreQuizActivity.this, QuizActivity.class);
            intent.putExtra("chapterName", chapterName);
            intent.putExtra("chapterId", chapterId);
            intent.putExtra("contentNumber", contentNumber);
            intent.putExtra("courseId", courseId);

            intent.putExtra("quiz",quiz);
            startActivity(intent);
        });
    }

    private void fetchQuizDetails(Long chapterId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<Quiz> call = apiInterface.getQuizByChapter(chapterId);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {

                    quiz = response.body();
                    Toast.makeText(PreQuizActivity.this, "Quiz details fetched successfully", Toast.LENGTH_SHORT).show();

                    handleFetchedData();
                } else {
                    // Handle unsuccessful response using Toast
                    Toast.makeText(PreQuizActivity.this, "Failed to fetch quiz details : err 1", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                // Handle failure
                Toast.makeText(PreQuizActivity.this, "Failed to fetch quiz details : err 2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}