package com.OM.EdJourney.ui.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.OM.EdJourney.R;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizCompletionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizCompletionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public QuizCompletionFragment() {
        // Required empty public constructor
    }

    public static QuizCompletionFragment newInstance() {
        QuizCompletionFragment fragment = new QuizCompletionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.quiz_completion_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView animatedImageView = view.findViewById(R.id.animatedImageView);

        Glide.with(this)
                .asGif()
                .load(R.drawable.star_badge)
                .into(animatedImageView);
    }

}