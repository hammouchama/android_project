package com.OM.EdJourney.ui.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Chapter;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;
import com.OM.EdJourney.ui.chapter.CourseChaptersList;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Video extends AppCompatActivity {
    ApiInterface apiInterface;
     YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        youTubePlayerView = findViewById(R.id.youtube_video);
        // Initialize the YouTubePlayerView with a lifecycle owner
        getLifecycle().addObserver(youTubePlayerView);
        Long chapterId;
        Intent intent = getIntent(); // Use getIntent() to get the Intent that started this activity

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

                youTubePlayer.loadVideo("-UlxHPIEVqA", 0);
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