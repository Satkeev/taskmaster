package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

public class ConfirmationActivity extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_confirmation);

            ((Button) findViewById(R.id.confirmation_button)).setOnClickListener(view -> {
                String username = ((TextView) findViewById(R.id.nameconfirm)).getText().toString();
                String verificationCode = ((TextView) findViewById(R.id.codeconfirm)).getText().toString();

                Amplify.Auth.confirmSignUp(
                        username,
                        verificationCode,
                        result -> {
                            Log.i("Amplify.confirm", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                            startActivity(new Intent(ConfirmationActivity.this, SignInActivity.class));
                        },
                        error -> Log.e("Amplify.confirm", error.toString())
                );
            });


        }
    }

