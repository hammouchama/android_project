package com.rajendra.courseapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajendra.courseapp.CoursePage;
import com.rajendra.courseapp.R;

import com.rajendra.courseapp.model.Course;

import java.util.List;


public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CategoryViewHolder> {

    private Context context;
    List<Course> courseList;

    public CoursesAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);


        // now here we create a recyclerview row items.
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        // here we will bind data in recyclerview ro items.

        holder.courseName.setText(courseList.get(position).getCourseName());
        holder.courseDescription.setText(courseList.get(position).getCourseDescription());
        holder.courseId.setText(courseList.get(position).getCourseId().toString());

        // for image we need to add glide image fetching library from network

        Glide.with(context).load(courseList.get(position).getImage()).into(holder.courseImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, CoursePage.class);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return courseList != null ? courseList.size() : 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView courseImage;
        TextView courseName,courseDescription,courseId;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            courseImage = itemView.findViewById(R.id.courseImage);
            courseName = itemView.findViewById(R.id.course_name);
            courseDescription = itemView.findViewById(R.id.course_Description);
            courseId=itemView.findViewById(R.id.cours_Id);


        }
    }

}
