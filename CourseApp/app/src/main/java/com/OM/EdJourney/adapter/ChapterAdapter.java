package com.OM.EdJourney.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.OM.EdJourney.ui.quiz.PreQuizActivity;
import com.OM.EdJourney.ui.course.Video;
import com.OM.EdJourney.ui.quiz.QuizActivity;
import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.CourseViewHolder> {

    Context context;
    List<Chapter> chapterList;
    Long courseId;
    String courseName;

    public ChapterAdapter(Context context, List<Chapter> chapterList, Long courseId, String courseName) {
        this.context = context;
        this.chapterList = chapterList;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list_chapters_items, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.chapterName.setText(chapterList.get(position).getChapterName());
        holder.contentNumber.setText(chapterList.get(position).getChapterId().toString());
        holder.contentTime.setText("40 min");

        holder.start_chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Video.class);
                intent.putExtra("chapterName", chapterList.get(position).getChapterName());
                intent.putExtra("chapterId", chapterList.get(position).getChapterId());
                intent.putExtra("contentNumber", Integer.valueOf(holder.contentNumber.getText().toString()));
                intent.putExtra("courseName", courseName);


                intent.putExtra("courseId", courseId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView chapterName,contentNumber,contentTime;
        ImageView start_chapter;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapter_name);
            contentNumber=itemView.findViewById(R.id.content_number);
            contentTime=itemView.findViewById(R.id.content_time);
            start_chapter=itemView.findViewById(R.id.start_chapter);
        }
    }
}
