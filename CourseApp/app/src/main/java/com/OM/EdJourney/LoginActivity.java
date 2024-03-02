package com.OM.EdJourney;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.OM.EdJourney.model.User;
import com.OM.EdJourney.retrofit.ApiInterface;
import com.OM.EdJourney.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    ApiInterface apiInterface;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);

        //Set the eye icon to the right of the password field
        loginPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_eye_off, 0); // Set initial eye icon to 'off'
        loginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (loginPassword.getRight() - loginPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<User> call = apiInterface.loginUser(new User(userUsername, userPassword));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userFromServer = response.body();
                    if (userFromServer != null) {

                        // Authentication successful
                        //save user info
                        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", userFromServer.getName());
                        editor.putString("email", userFromServer.getEmail());
                        editor.putString("username", userFromServer.getUsername());
                        editor.putString("password", userFromServer.getPassword());
                        editor.putString("id", userFromServer.getId().toString());
                        editor.apply();

                        //
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", userFromServer.getName());
                        intent.putExtra("email", userFromServer.getEmail());
                        intent.putExtra("username", userFromServer.getUsername());
                        intent.putExtra("password", userFromServer.getPassword());
                        startActivity(intent);
                    } else {
                        // Handle invalid response from the server
                        Toast.makeText(LoginActivity.this, "Invalid response from the server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful login, show appropriate error messages
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure (e.g., network issues)
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // If password is currently visible, change input type to hide it and change eye icon to 'off'
            loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            loginPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_eye_off, 0);
            isPasswordVisible = false;
        } else {
            // If password is currently hidden, change input type to show it and change eye icon to 'on'
            loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, R.drawable.ic_eye, 0);
            isPasswordVisible = true;
        }

        // Move cursor to end of text
        loginPassword.setSelection(loginPassword.getText().length());
    }

}
