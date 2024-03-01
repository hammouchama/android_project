package com.OM.EdJourney.ui.quiz;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.databinding.QuizActivityBinding;
import com.OM.EdJourney.databinding.QuizFragmentBinding;
import com.OM.EdJourney.model.Question;
import com.OM.EdJourney.model.Quiz;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class QuizFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";
    private static final String ARG_CHAPTER_ID = "chapter_id";
    private static final String ARG_QUIZ = "quiz";

    private Long chapterId;
    private QuizFragmentBinding binding;
    // Question list
    private List<Question> questionList;
    private int questionNumber=0;
    private int selectedOption = 0; //0 for none
    private Quiz quiz;
    private int score = 0;
    private int correctAnswer;
    private CountDownTimer countDownTimer;

    private QuizActivity quizActivity;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(Long chapterId,Quiz quiz) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_NUMBER, 0);
        args.putLong(ARG_CHAPTER_ID, chapterId);
        args.putSerializable(ARG_QUIZ, quiz);
        fragment.setArguments(args);
        //Toast to display the chapterId and questionNumber
        //Toast.makeText(fragment.getContext(), "Chapter ID: " + chapterId + " Question Number: " + questionNumber, Toast.LENGTH_SHORT).show();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = QuizFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            questionNumber = getArguments().getInt(ARG_QUESTION_NUMBER);
            chapterId = getArguments().getLong(ARG_CHAPTER_ID);
            quiz = (Quiz) getArguments().getSerializable(ARG_QUIZ);
            questionList = quiz.getQuestions();

            quizActivity = (QuizActivity) getActivity();


            // get the time
            int timePerQuestion = quiz.getTimePerQuestion();
            countDownTimer = new CountDownTimer(timePerQuestion* 1000L, 1000) {
                // Executes on each tick (every second in this case)
                public void onTick(long millisUntilFinished) {
                    // Update your UI to display the remaining time
                    quizActivity.updateTimerUI(millisUntilFinished);
                }

                // Executes when the timer finishes
                public void onFinish() {
                    // Handle what happens when the timer finishes
                    handleTimerFinish();
                }
            };

            // set the numberOfQuestions
            binding.numberOfQuestions.setText(getTwoDigitsNumber(questionList.size()));

            displayQuestion(questionNumber);


        }else{
            Toast.makeText(getContext(), "Failed to fetch quiz details : err 4", Toast.LENGTH_SHORT).show();

        }
    }

    private void handleTimerFinish() {
        // Handle what happens when the timer finishes
        // For example, mark the question as not answered
        Toast.makeText(getContext(), "Time's up! Try again.", Toast.LENGTH_SHORT).show();

        handleUserSelection();

    }
    private void displayQuestion(int questionNumber) {
        // TODO: Fetch the question and options based on the questionNumber
        // For simplicity, let's assume you have a Question class with appropriate methods.

        //Question question = fetchQuestion(questionNumber);
        Question question = questionList.get(questionNumber);

        // set questionNumber textview
        binding.questionNumber.setText(getTwoDigitsNumber(questionNumber + 1));

        // set progress
        binding.progressBar.setProgress((questionNumber + 1) * 100 / questionList.size());
        countDownTimer.start();

        // Set question text
        binding.questionTextView.setText(question.getQuestionText());

        int numOfOptions = question.getOption1() != null ? 1 : 0;
        numOfOptions += question.getOption2() != null ? 1 : 0;
        numOfOptions += question.getOption3() != null ? 1 : 0;
        numOfOptions += question.getOption4() != null ? 1 : 0;

        String[] options = new String[numOfOptions];
        int j=0;
        if(question.getOption1()!=null)
            options[j++]=question.getOption1();
        if(question.getOption2()!=null)
            options[j++]=question.getOption2();
        if(question.getOption3()!=null)
            options[j++]=question.getOption3();
        if(question.getOption4()!=null)
            options[j++]=question.getOption4();


        binding.optionA.setVisibility(View.VISIBLE);
        // loop on the number of other inexistant option to delete it from showing up
        for(int z=0;z<j;z++){
            if(z==0)
                binding.optionA.setVisibility(View.VISIBLE);
            else if(z==1)
                binding.optionB.setVisibility(View.VISIBLE);
            else if(z==2)
                binding.optionC.setVisibility(View.VISIBLE);
            else if(z==3)
                binding.optionD.setVisibility(View.VISIBLE);
        }
        for(int z=j;z<4;z++){
            if(z==0)
                binding.optionA.setVisibility(View.GONE);
            else if(z==1)
                binding.optionB.setVisibility(View.GONE);
            else if(z==2)
                binding.optionC.setVisibility(View.GONE);
            else if(z==3)
                binding.optionD.setVisibility(View.GONE);
        }


        // shuffle the array but keep track on the correct answer
        correctAnswer = question.getCorrectOption()-1;
        int[] positions = randomPositionArray(j);
        for (int i = 0; i < options.length; i++) {
            if (i == 0) {
                binding.optionATextView.setText(options[positions[i]]);
            } else if (i == 1) {
                binding.optionBTextView.setText(options[positions[i]]);
            } else if (i == 2) {
                binding.optionCTextView.setText(options[positions[i]]);
            } else if (i == 3) {
                binding.optionDTextView.setText(options[positions[i]]);
            }
        }


        // Implement logic to handle user selection and proceed to the next question
        // For example, you can add click listeners to the option views.

        binding.optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption= 1;
                toggleOption(selectedOption);
            }
        });

        binding.optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption= 2;
                toggleOption(selectedOption);
            }
        });

        binding.optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption= 3;
                toggleOption(selectedOption);
            }
        });

        binding.optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption= 4;
                toggleOption(selectedOption);
            }
        });

        // next_question clicklistener
        binding.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOption==0){
                    Toast.makeText(getContext(), "Please select an option", Toast.LENGTH_SHORT).show();
                }else{
                    handleUserSelection();
                }
            }
        });


    }

    private void handleUserSelection() {
        // Implement logic to handle user selection and proceed to the next question
        // For example, you can add click listeners to the option views.
        // If the selected option is correct, display a message and proceed to the next question.
        // If the selected option is incorrect, display a message and allow the user to try again.

        Log.i("selectedOption", selectedOption + "");
        Log.i("correctAnswer", correctAnswer + "");


        if (selectedOption == correctAnswer) {
            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(getContext(), "Incorrect. Try again!", Toast.LENGTH_SHORT).show();
        }


        if (questionNumber < questionList.size() - 1) {
            displayQuestion(questionNumber + 1);
            selectedOption=0; // reset the selected option
            toggleOption(selectedOption); // reset the options
            questionNumber++;
        } else {
            // Quiz complete
            Toast.makeText(getContext(), "Quiz complete", Toast.LENGTH_SHORT).show();
            questionNumber= 0;
            quizActivity.completeQuiz();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel(); // Stop the timer to avoid leaks
    }

    // toggle option function by changing the option*_radio image source to ic_radiobox_blank or ic_radiobox_marked
    private void toggleOption(int selectedOption){

        binding.optionARadio.setImageResource(R.drawable.ic_radiobox_blank);
        binding.optionBRadio.setImageResource(R.drawable.ic_radiobox_blank);
        binding.optionCRadio.setImageResource(R.drawable.ic_radiobox_blank);
        binding.optionDRadio.setImageResource(R.drawable.ic_radiobox_blank);
        if(selectedOption==1){
            binding.optionARadio.setImageResource(R.drawable.ic_radiobox_marked);
        }else if(selectedOption==2){
            binding.optionBRadio.setImageResource(R.drawable.ic_radiobox_marked);
        }else if(selectedOption==3){
            binding.optionCRadio.setImageResource(R.drawable.ic_radiobox_marked);
        }else if(selectedOption==4){
            binding.optionDRadio.setImageResource(R.drawable.ic_radiobox_marked);
        }
    }
    public String getTwoDigitsNumber(long number){
        if(number<10){
            return "0"+number;
        }
        return String.valueOf(number);
    }




    private int[] randomPositionArray(int length) {
        int[] positions = new int[length];
        for (int i = 0; i < length; i++) {
            positions[i] = i;
        }
        for (int i = 0; i < length; i++) {
            int randomPosition = (int) (Math.random() * length);
            int temp = positions[i];
            positions[i] = positions[randomPosition];
            positions[randomPosition] = temp;
        }
        // example of output : [2, 0, 3, 1]
        return positions;

    }

}