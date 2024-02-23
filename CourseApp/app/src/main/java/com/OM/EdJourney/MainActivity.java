package com.rajendra.courseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.rajendra.courseapp.adapter.CoursesAdapter;
import com.rajendra.courseapp.model.Category;
import com.rajendra.courseapp.model.Course;
import com.rajendra.courseapp.retrofit.ApiInterface;
import com.rajendra.courseapp.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    CoursesAdapter categoryAdapter;

    ApiInterface apiInterface;

    ImageView Logout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check if user logged
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);

        if (!preferences.contains("name") || !preferences.contains("email") || !preferences.contains("username") || !preferences.contains("password")) {
            // User information is not available, redirect to LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Optional: Finish the current activity to prevent returning to it on back press
            return;
        }

        setContentView(R.layout.activity_main);

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        categoryRecyclerView = findViewById(R.id.course_recycler);

        Call<List<Course>> call = apiInterface.getAllCourses();


        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    List<Course> categoryList = response.body();
                    Log.d("Debug", "Course list size: " + categoryList.size());
                    if (categoryList != null) {
                        getAllCategory(categoryList);
                    } else {
                        Toast.makeText(MainActivity.this, "Empty course list", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get course list", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                // Log the error
                Log.e("NetworkError", "Error: " + t.getMessage(), t);
            }
        });


        Logout = findViewById(R.id.logout);
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


        /** Side bar **/
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Handle click on hamburger icon
        findViewById(R.id.toggleMenuIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        TextView fullNameView = headerView.findViewById(R.id.fullName);
        TextView emailView = headerView.findViewById(R.id.email);


        fullNameView.setText("name");
        emailView.setText("email");

        // enable sliding the menu back and forth




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item clicks here
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_profile:
                        /*Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(profileIntent);*/
                        break;
                    case R.id.nav_logout:
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
                        break;
                }
                // Close the drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        /** END Side bar **/

    }


    private void getAllCategory(List<Course> categoryList) {

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CoursesAdapter(this, categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

    }


    /**
     * Side bar
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /** END Side bar **/
}
