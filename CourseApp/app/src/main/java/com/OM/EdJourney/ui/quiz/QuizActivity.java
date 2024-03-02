package com.OM.EdJourney.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;

public class QuizActivity extends AppCompatActivity {
    private FrameLayout quizContainer;
    private Long chapterId, courseId, contentNumber;
    private String chapterName, courseName = "None";
    Quiz quiz;
    TextView timerTextView;

    FragmentManager fragmentManager;

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        quizContainer = findViewById(R.id.quizContainer);

        timerTextView = findViewById(R.id.timerTextView);

        setStatusBarColor(R.color.colorSecondaryDark);


        // Retrieve chapterId from Intent
        chapterId = getIntent().getLongExtra("chapterId", 0L);
        if (chapterId == 0L) {
            // If chapterId is not provided, return
            Toast.makeText(this, "Chapter ID not provided", Toast.LENGTH_SHORT).show();
            return;
        }
        courseId = getIntent().getLongExtra("courseId", 0L);
        contentNumber = getIntent().getLongExtra("contentNumber", 0L);
        chapterName = getIntent().getStringExtra("chapterName");
        quiz = (Quiz) getIntent().getSerializableExtra("quiz");
        courseName = getIntent().getStringExtra("courseName");


        // Display the first quiz question




        //completeQuiz(5, 10);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        showQuizFragment(chapterId, quiz);
    }

    private void showQuizFragment(Long chapterId, Quiz quiz) {
        QuizFragment quizFragment = QuizFragment.newInstance(chapterId, quiz);

        transaction.replace(R.id.quizContainer, quizFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public String getTwoDigitsNumber(long number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public void updateTimerUI(long millisUntilFinished) {
        // Update your UI to display the remaining time
        timerTextView.setText(String.valueOf(getTwoDigitsNumber(millisUntilFinished / 1000)));
    }

    // function when complete the quiz
    public void completeQuiz(int numberOfCorrectAnswers, int numberOfQuestions) {
        // make the timer invisible
        LinearLayout timerLayout = findViewById(R.id.timerLayout);
        timerLayout.setVisibility(TextView.INVISIBLE);

        int scorePercentage = (numberOfCorrectAnswers * 100) / numberOfQuestions;
        int requiredScore = quiz.getRequiredScore();


        // Display the quiz completion activity depending on the score if higher or equal than requiredScore or not
        Intent intent;
        if (scorePercentage >= requiredScore) {
            // Display the quiz completion passed activity
            intent = new Intent(QuizActivity.this, QuizCompletionPassed.class);
        } else {
            // Display the quiz completion failed activity
            intent = new Intent(QuizActivity.this, QuizCompletionFailed.class);
        }
        intent.putExtra("scorePercentage", scorePercentage);
        intent.putExtra("requiredScore", requiredScore);

        intent.putExtra("chapterId", chapterId);
        intent.putExtra("courseId", courseId);
        intent.putExtra("contentNumber", contentNumber);
        intent.putExtra("chapterName", chapterName);
        intent.putExtra("courseName", courseName);
        intent.putExtra("quiz", quiz);
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