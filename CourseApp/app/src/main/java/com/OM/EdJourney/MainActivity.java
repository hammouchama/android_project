package com.OM.EdJourney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.OM.EdJourney.adapter.CoursesAdapter;
import com.OM.EdJourney.model.Course;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity {

    RecyclerView categoryRecyclerView;
    CoursesAdapter categoryAdapter;

    ApiInterface apiInterface;
    TextView fullName;

    ImageView Logout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private DrawerLayout drawerLayout;
    Long userId;
    Long chapterCount;
    TextView chapterCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check if user logged
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);

        if (!preferences.contains("name") || !preferences.contains("email") || !preferences.contains("username") || !preferences.contains("password")) {
            // User information is not available, redirect to LoginActivity
            Intent loginIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(loginIntent);
            finish(); // Optional: Finish the current activity to prevent returning to it on back press
            return;
        }
        String tmp =  preferences.getString("id", "0");
        userId = Long.parseLong(tmp);

        setContentView(R.layout.activity_main);

        fullName=findViewById(R.id.user_name);
        fullName.setText("Hi, "+preferences.getString("name"," "));

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        categoryRecyclerView = findViewById(R.id.course_recycler);

        chapterCountTextView = findViewById(R.id.chapterCountTextView);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        // Enable the back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_format_list_bulleted_type); // You can replace ic_arrow_back with your own drawable
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }

        Call<List<Course>> call = apiInterface.getAllCourses();


        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    List<Course> categoryList = response.body();
                    Log.d("Debug", "Course list size: " + categoryList.size());
                    if (categoryList != null) {
                        getAllCategory(categoryList);
                        getCompletedChaptersCount();
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


       /*Logout = findViewById(R.id.logout);
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
                Intent loginIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(loginIntent);

                // Optional: Finish the current activity to prevent returning to it on back press
                finish();
            }
        });*/
        /** Side bar **/

        drawerLayout = findViewById(R.id.drawer_layout);




        // Handle click on hamburger icon
        /*ImageView toggleMenuIcon = findViewById(R.id.toggleMenuIcon);
        toggleMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

         */


        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);

        TextView fullNameView = headerView.findViewById(R.id.fullName);
        TextView emailView = headerView.findViewById(R.id.email);


        fullNameView.setText(preferences.getString("name"," "));
        emailView.setText(preferences.getString("email"," "));


        // enable sliding the menu back and forth




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle item clicks here
                Log.i("item.getItemId()",item.getItemId()+"");
                Log.i("R.id.nav_logout",R.id.nav_logout+"");
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
                        Intent loginIntent = new Intent(MainActivity.this, WelcomeActivity.class);
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

    // get count of all chapters by userId
    private void getCompletedChaptersCount() {
        Call<Integer> call = apiInterface.getCompletedChaptersCount(userId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer count = response.body();
                    chapterCount = count.longValue();
                    Log.d("Debug", "Completed chapters count: " + count);

                    // put chapterCount in chapterCountTextView
                    chapterCountTextView.setText(getTwoDigitsNumber(chapterCount));

                } else {
                    Toast.makeText(MainActivity.this, "Failed to get completed chapters count", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show();
                }
                // Log the error
                Log.e("NetworkError", "Error: " + t.getMessage(), t);
            }
        });
    }


    public String getTwoDigitsNumber(long number) {
        if (number < 10) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    /**
     * Side bar
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    /** END Side bar **/
    // Handle back button click event
    @Override
    public boolean onSupportNavigateUp() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}
