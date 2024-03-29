package com.OM.EdJourney.ui.course;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Chapter;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.OM.EdJourney.ui.quiz.PreQuizActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video extends AppCompatActivity {
    ApiInterface apiInterface;
    YouTubePlayerView youTubePlayerView;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        youTubePlayerView = findViewById(R.id.youtube_video);

        //bar action
        Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        // Enable the back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left); // You can replace ic_arrow_back with your own drawable
            upArrow.setColorFilter(getResources().getColor(R.color.lavender), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
        Intent intent = getIntent();
        actionBar.setTitle(Objects.requireNonNull(intent.getStringExtra("chapterName")));


        // Initialize the YouTubePlayerView with a lifecycle owner
        getLifecycle().addObserver(youTubePlayerView);
        Long chapterId;

        if (intent != null && intent.hasExtra("chapterId")) {
            chapterId = intent.getLongExtra("chapterId", 0); // Provide a default value if "courseId" is not present

        } else {
            chapterId = null;
            return; // You may want to handle this case appropriately
        }


        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<Chapter> call = apiInterface.getChapter(chapterId);
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                Chapter chapter = response.body();
                // Assuming you want a vertical list, use LinearLayoutManager
                setVideoID(chapter);
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                Toast.makeText(Video.this, "Failed to fetch chapters", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a callback to be notified when the player is ready
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                // Load the YouTube video by providing the video ID

              //  youTubePlayer.loadVideo("-UlxHPIEVqA", 0);
                youTubePlayerView.enableBackgroundPlayback(true);
                youTubePlayerView.setEnableAutomaticInitialization(true);
                youTubePlayerView.setFitsSystemWindows(true);
                youTubePlayerView.setMinimumHeight(380);
            }

            @Override
            public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState state) {
                // Handle player state changes if needed
            }
        });


        // handle click on to_quiz
        findViewById(R.id.to_quiz).setOnClickListener(view -> {
            Intent intent1 = new Intent(Video.this, PreQuizActivity.class);
            intent1.putExtra("chapterName", intent.getStringExtra("chapterName"));
            intent1.putExtra("chapterId", intent.getLongExtra("chapterId", 0L));
            intent1.putExtra("contentNumber", intent.getLongExtra("contentNumber", 0L));
            intent1.putExtra("courseId", intent.getLongExtra("courseId", 0L));
            intent1.putExtra("courseName", intent.getStringExtra("courseName"));

            startActivity(intent1);
        });
    }
    // Handle back button click event
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setVideoID(Chapter chapter) {
        if (youTubePlayerView != null) {
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                // Load the new YouTube video by providing the video ID
                youTubePlayer.loadVideo(chapter.getVideoID(), 0);
            });
        }
    }
}