package com.OM.EdJourney.ui.quiz;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

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

    private Long chapterId;
    private QuizFragmentBinding binding;
    // Question list
    private List<Question> questionList;
    private int questionNumber;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(Long chapterId) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_NUMBER, 0);
        args.putLong(ARG_CHAPTER_ID, chapterId);
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

            fetchQuizDetails(chapterId);

        }
    }

    private void displayQuestion(int questionNumber) {
        // TODO: Fetch the question and options based on the questionNumber
        // For simplicity, let's assume you have a Question class with appropriate methods.

        //Question question = fetchQuestion(questionNumber);
        Question question = questionList.get(questionNumber);

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

        // loop on the number of other inexistant option to delete it from showing up
        for(int z=j;z<4;z++){
            if(z==0)
                binding.optionATextView.setVisibility(View.GONE);
            else if(z==1)
                binding.optionBTextView.setVisibility(View.GONE);
            else if(z==2)
                binding.optionCTextView.setVisibility(View.GONE);
            else if(z==3)
                binding.optionDTextView.setVisibility(View.GONE);
        }


        // shuffle the array but keep track on the correct answer
        int correctAnswer = question.getCorrectOption()-1;
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

        binding.optionATextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserSelection(positions[0], questionNumber, questionList, correctAnswer);
            }
        });

        binding.optionBTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserSelection(positions[1], questionNumber, questionList, correctAnswer);
            }
        });

        binding.optionCTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserSelection(positions[2], questionNumber, questionList, correctAnswer);
            }
        });

        binding.optionDTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUserSelection(positions[3], questionNumber, questionList, correctAnswer);
            }
        });


    }

    private void handleUserSelection(int selectedOption, int questionNumber, List<Question> questionList, int correctAnswer) {
        // Implement logic to handle user selection and proceed to the next question
        // For example, you can add click listeners to the option views.
        // If the selected option is correct, display a message and proceed to the next question.
        // If the selected option is incorrect, display a message and allow the user to try again.

        Log.i("selectedOption", selectedOption + "");
        Log.i("correctAnswer", correctAnswer + "");

        if (selectedOption == correctAnswer) {
            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            // Proceed to the next question
            if (questionNumber < questionList.size() - 1) {
                displayQuestion(questionNumber + 1);
                this.questionNumber++;
            } else {
                // Quiz complete
                Toast.makeText(getContext(), "Quiz complete", Toast.LENGTH_SHORT).show();
                questionNumber= 0;
            }
        } else {
            Toast.makeText(getContext(), "Incorrect. Try again!", Toast.LENGTH_SHORT).show();
        }
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

    /*
    private Question fetchQuestion(int questionNumber) {
        // This is a placeholder method. Replace it with your logic to fetch questions.
        // You might want to store questions in a list or database.
        // Return a Question object with appropriate methods.
        return new Question("Sample Question", "Option A", "Option B", "Option C", "Option D",1);
    }*/

    private void fetchQuizDetails(Long chapterId) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<Quiz> call = apiInterface.getQuizByChapter(chapterId);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {

                    Quiz currentQuiz = response.body();
                    // Store the question list
                    questionList = currentQuiz.getQuestions();

                    // Display the first question
                    displayQuestion(0);
                    //Toast
                    Toast.makeText(getContext(), "Quiz details fetched successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response using Toast
                    Toast.makeText(getContext(), "Failed to fetch quiz details", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                // Handle failure
            }
        });
    }
}