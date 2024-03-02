package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.model.User;
import com.OM.EdJourney.model.UserProgress;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizCompletionPassed extends AppCompatActivity {
    private int scorePercentage = 0;

    private Long chapterId, courseId, contentNumber;
    private String chapterName, courseName = "None";
    private Long userId = 1L;
    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion_passed);


        setStatusBarColor(R.color.colorSecondaryDark);

        ImageView animatedImageView = findViewById(R.id.animatedImageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.star_badge)
                .into(animatedImageView);


        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);

        userId = preferences.getLong("id", 1L);

        chapterId = getIntent().getLongExtra("chapterId", 0L);
        courseId = getIntent().getLongExtra("courseId", 0L);
        contentNumber = getIntent().getLongExtra("contentNumber", 0L);
        chapterName = getIntent().getStringExtra("chapterName");
        quiz = (Quiz) getIntent().getSerializableExtra("quiz");


        scorePercentage = getIntent().getIntExtra("scorePercentage", 0);
        // set total score
        TextView totalScoreTextView = findViewById(R.id.score);
        totalScoreTextView.setText(String.valueOf(scorePercentage) + "%");


        findViewById(R.id.retryButton).setOnClickListener(v -> {
            goToPreQuizActivity();
        });
        findViewById(R.id.nextChapterButton).setOnClickListener(v -> {
            goToCourseChaptersListActivity();
        });

        // Save progress
        saveProgress();
    }

    private void saveProgress() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        UserProgress userProgress = new UserProgress(userId, courseId, chapterId);
        Log.i("UserProgress", userProgress.toString());
        Call<Void> call = apiInterface.addProgress(userProgress);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    System.out.println("Progress saved successfully");
                    Toast.makeText(QuizCompletionPassed.this, "Progress saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Progress not saved");
                    Toast.makeText(QuizCompletionPassed.this, "Progress not saved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Progress not saved");
            }
        });


    }


    // function to finish all frames and go back to prequiz activity
    public void goToPreQuizActivity() {
        Intent intent = new Intent(QuizCompletionPassed.this, PreQuizActivity.class);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("contentNumber", contentNumber);
        intent.putExtra("chapterName", chapterName);
        intent.putExtra("courseName", courseName);
        ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // function to finish all frames and go back to coursechapterslist
    public void goToCourseChaptersListActivity() {
        Intent intent = new Intent(QuizCompletionPassed.this, CourseChaptersList.class);
        //courseId
        intent.putExtra("courseId", courseId);
        //courseName
        intent.putExtra("courseName", courseName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        finish();
    }


    protected void setStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorResId));
        }
    }
}