package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.model.UserProgress;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.bumptech.glide.Glide;

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

        // Save progress
        saveProgress();

        ImageView animatedImageView = findViewById(R.id.animatedImageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.star_badge)
                .into(animatedImageView);


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
    }

    private void saveProgress() {
        UserProgress userProgress = new UserProgress(userId, courseId, chapterId);


    }


    // function to finish all frames and go back to prequiz activity
    public void goToPreQuizActivity() {
        Intent intent = new Intent(QuizCompletionPassed.this, PreQuizActivity.class);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("contentNumber", contentNumber);
        intent.putExtra("chapterName", chapterName);
        intent.putExtra("courseName", courseName);;
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
}