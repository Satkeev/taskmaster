package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

public class SignInActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);

            ((Button) findViewById(R.id.login_button)).setOnClickListener(view ->{
                String username = ((TextView) findViewById(R.id.editTextTextPersonName2)).getText().toString();
                String password = ((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString();

                Amplify.Auth.signIn(
                        username,
                        password,
                        result -> {
                            Log.i("Amplify.login", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        },
                        error -> Log.e("Amplify.login", error.toString())
                );
            });


        }
    }
