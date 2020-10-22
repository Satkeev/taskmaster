package com.satkeev.github.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.widget.Toast.LENGTH_LONG;


public class Settings extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_settings);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);// getter
            final SharedPreferences.Editor preferenceEditor = preferences.edit();

            findViewById(R.id.username_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText name = Settings.this.findViewById(R.id.editTextName);
                    preferenceEditor.putString("namePotato", name.getText().toString());
                    preferenceEditor.apply();
                    System.out.println(name.getText().toString());

//                    Toast toast = Toast.makeText(this, "You saved your name", Toast.LENGTH_LONG);
//
//                    toast.show();

                }
            });
        }
    }