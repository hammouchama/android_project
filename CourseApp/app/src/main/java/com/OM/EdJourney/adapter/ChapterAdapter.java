package com.OM.EdJourney.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.OM.EdJourney.R;
import com.OM.EdJourney.model.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.CourseViewHolder> {

    Context context;
    List<Chapter> chapterList;

    public ChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_course_chapters_list, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.chapterName.setText(chapterList.get(position).getChapterName());
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView chapterName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterName = itemView.findViewById(R.id.chapter_name);
        }
    }
}
