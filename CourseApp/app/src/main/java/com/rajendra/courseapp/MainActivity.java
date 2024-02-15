package com.rajendra.courseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rajendra.courseapp.adapter.CategoryAdapter;
import com.rajendra.courseapp.model.Category;
import com.rajendra.courseapp.retrofit.ApiInterface;
import com.rajendra.courseapp.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;

    ApiInterface apiInterface;

    ImageView Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //check if user logged
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);

        if (!preferences.contains("name") || !preferences.contains("email")
                || !preferences.contains("username") || !preferences.contains("password")) {
            // User information is not available, redirect to LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Optional: Finish the current activity to prevent returning to it on back press
            return;
        }

        setContentView(R.layout.activity_main);

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        categoryRecyclerView = findViewById(R.id.course_recycler);

        Call<List<Category>> call = apiInterface.getAllCategory();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categoryList = response.body();
                    Log.d("Debug", "Category list size: " + categoryList.size());
                    if (categoryList != null) {
                        getAllCategory(categoryList);
                    } else {
                        Toast.makeText(MainActivity.this, "Empty category list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get category list", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                // Log the error
                Log.e("NetworkError", "Error: " + t.getMessage(), t);
            }
        });

        //lets run this app and check server is responding or not.
        // we have successfully fetched data from api.
        // now we will setup this json response to recyclerview.

        Logout=findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Clear user information from SharedPreferences
                SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("name");
                editor.remove("email");
                editor.remove("username");
                editor.remove("password");
                editor.apply();

                // Redirect to LoginActivity
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);

                // Optional: Finish the current activity to prevent returning to it on back press
                finish();
            }
        });

    }


    // welcome to all of you.
    // first of all i am goinf to import some assets

    // now we will setup Retrofit for network call fetching data from server.
    // lets import retrofit dependency

    private void getAllCategory(List<Category> categoryList){

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }

    // now again we need to create a model class for data and adapter class for recycler view
    // lest have a look on json data
    // this data comming from server having course content details
    // lets do it fast.

    // tutorial has benn complited see you the next tutorial.

}
