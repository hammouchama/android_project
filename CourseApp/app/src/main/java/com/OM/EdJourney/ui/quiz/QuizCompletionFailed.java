package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.bumptech.glide.Glide;

public class QuizCompletionFailed extends AppCompatActivity {
    private int scorePercentage = 0,requiredScore = 80;

    private Long chapterId, courseId, contentNumber;
    private String chapterName, courseName = "None";
    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion_failed);


        setStatusBarColor(R.color.colorSecondaryDark);

        ImageView animatedImageView = findViewById(R.id.animatedImageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.go_for_it)
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

        // set tvMessage2
        TextView tvMessage2 = findViewById(R.id.tvMessage2);
        tvMessage2.setText("You need to achieve at least "+requiredScore+"% to pass the quiz. Retry and aim for success!");


        findViewById(R.id.retryButton).setOnClickListener(v -> {
            goToPreQuizActivity();
        });
        findViewById(R.id.backToChapterListButton).setOnClickListener(v -> {
            goToCourseChaptersListActivity();
        });
    }

    // function to finish all frames and go back to prequiz activity
    public void goToPreQuizActivity() {
        Intent intent = new Intent(QuizCompletionFailed.this, PreQuizActivity.class);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("contentNumber", contentNumber);
        intent.putExtra("chapterName", chapterName);
        intent.putExtra("courseName", courseName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // function to finish all frames and go back to coursechapterslist
    public void goToCourseChaptersListActivity() {
        Intent intent = new Intent(QuizCompletionFailed.this, CourseChaptersList.class);
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