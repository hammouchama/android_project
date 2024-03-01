package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.ui.quiz.QuizFragment;

public class QuizActivity extends AppCompatActivity {
    private FrameLayout quizContainer;
    private Long chapterId, courseId, contentNumber;
    private String chapterName;
    Quiz quiz;
    TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        quizContainer = findViewById(R.id.quizContainer);

        timerTextView = findViewById(R.id.timerTextView);

        // Make the timer visible
        /*LinearLayout timerLayout = findViewById(R.id.timerLayout);
        timerLayout.setVisibility(TextView.VISIBLE);*/

        // Retrieve chapterId from Intent
        chapterId = getIntent().getLongExtra("chapterId", 0L);
        if(chapterId == 0L) {
            // If chapterId is not provided, return
            Toast.makeText(this, "Chapter ID not provided", Toast.LENGTH_SHORT).show();
            return;
        }
        courseId = getIntent().getLongExtra("courseId", 0L);
        contentNumber = getIntent().getLongExtra("contentNumber",0L);
        chapterName = getIntent().getStringExtra("chapterName");
        quiz = (Quiz) getIntent().getSerializableExtra("quiz");


        // Display the first quiz question
        showQuizFragment(chapterId, quiz);
    }

    private void showQuizFragment(Long chapterId, Quiz quiz) {
        QuizFragment quizFragment = QuizFragment.newInstance(chapterId, quiz);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, quizFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public String getTwoDigitsNumber(long number){
        if(number<10){
            return "0"+number;
        }
        return String.valueOf(number);
    }
    public void updateTimerUI(long millisUntilFinished) {
        // Update your UI to display the remaining time
        timerTextView.setText(String.valueOf(getTwoDigitsNumber(millisUntilFinished / 1000)));
    }

    // function when complete the quiz
    public void completeQuiz() {
        // make the timer invisible
        LinearLayout timerLayout = findViewById(R.id.timerLayout);
        timerLayout.setVisibility(TextView.INVISIBLE);
        // Display the quiz completion fragment
        QuizCompletionFragment quizCompletionFragment = QuizCompletionFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, quizCompletionFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}