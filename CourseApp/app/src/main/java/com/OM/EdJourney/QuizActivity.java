package com.OM.EdJourney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.OM.EdJourney.ui.quiz.QuizFragment;

public class QuizActivity extends AppCompatActivity {
    private FrameLayout quizContainer;
    private ProgressBar progressBar;
    private Long chapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        quizContainer = findViewById(R.id.quizContainer);
        progressBar = findViewById(R.id.progressBar);

        // Retrieve chapterId from Intent
        chapterId = getIntent().getLongExtra("CHAPTER_ID", 0L);
        if(chapterId == 0L) {
            // If chapterId is not provided, return
            return;
        }

        // Display the first quiz question
        showQuizFragment(1, chapterId);
    }

    private void showQuizFragment(int questionNumber, Long chapterId) {
        QuizFragment quizFragment = QuizFragment.newInstance(questionNumber,chapterId);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, quizFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Update progress
        progressBar.setProgress(questionNumber);
    }
}