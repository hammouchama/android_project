package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.ui.quiz.QuizFragment;

public class QuizActivity extends AppCompatActivity {
    private FrameLayout quizContainer;
    private ProgressBar progressBar;
    private Long chapterId, courseId;
    private String chapterName , contentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        quizContainer = findViewById(R.id.quizContainer);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve chapterId from Intent
        chapterId = getIntent().getLongExtra("chapterId", 0L);
        if(chapterId == 0L) {
            // If chapterId is not provided, return
            Toast.makeText(this, "Chapter ID not provided", Toast.LENGTH_SHORT).show();
            return;
        }
        courseId = getIntent().getLongExtra("courseId", 0L);
        contentNumber = getIntent().getStringExtra("contentNumber");
        chapterName = getIntent().getStringExtra("chapterName");

        // Display the first quiz question
        showQuizFragment(chapterId);
    }

    private void showQuizFragment(Long chapterId) {
        QuizFragment quizFragment = QuizFragment.newInstance(chapterId);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, quizFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Update progress
        progressBar.setProgress(0);
    }
}