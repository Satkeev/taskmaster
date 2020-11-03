package com.satkeev.github.taskmaster;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class Signup extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            ((Button) findViewById(R.id.signuppage_button)).setOnClickListener(view -> {
                String username = ((TextView) findViewById(R.id.update_name)).getText().toString();
                String email = ((TextView) findViewById(R.id.emailname)).getText().toString();
                String password = ((TextView) findViewById(R.id.passwordname)).getText().toString();

                Amplify.Auth.signUp( // verification is 040893
                        username,
                        password,
                        AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build(),
                        result -> {
                            Log.i("Amplify.signup", "Result: " + result.toString());
                            startActivity(new Intent(Signup.this, ConfirmationActivity.class));
                        },
                        error -> Log.e("Amplify.signup", "Sign up failed", error)
                );
            });
        }
    }

