package com.rajendra.courseapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rajendra.courseapp.CourseDetailsActivity;
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
       // holder.courseDescription.setText(courseList.get(position).getCourseDescription());
        //holder.courseId.setText(courseList.get(position).getCourseId().toString());

        //holder.courseDescription.setVisibility(View.GONE);


       Glide.with(context)
                .load(courseList.get(position).getImage())
                .centerCrop()
                .into(holder.courseImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, CourseDetailsActivity.class);
                i.putExtra("courseId",courseList.get(position).getCourseId());
                i.putExtra("courseName",holder.courseName.getText());
                i.putExtra("courseDescription",courseList.get(position).getCourseDescription());
                i.putExtra("courseImage",courseList.get(position).getImage());
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
        TextView courseName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            courseImage = itemView.findViewById(R.id.courseImage);
            courseName = itemView.findViewById(R.id.course_name);

        }
    }

}
