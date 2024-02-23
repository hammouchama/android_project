package com.rajendra.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        Button lets_go=findViewById(R.id.lets_go);

        lets_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
