package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = MainActivity.this.findViewById(R.id.button1);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("add task");
                Intent addTask = new Intent(MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(addTask);
            }
        });
        Button soccerTickets = MainActivity.this.findViewById(R.id.soccer_tickets);
        soccerTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                String soccerText = b.getText().toString();
                System.out.println("soccer tickets");
                Intent soccerTickets = new Intent(MainActivity.this, TaskDetails.class);
                soccerTickets.putExtra("soccer", soccerText);
                MainActivity.this.startActivity(soccerTickets);

            }

        });
        Button soccerSchedule = MainActivity.this.findViewById(R.id.soccer_schedule);
        soccerSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                String soccerText = b.getText().toString();
                System.out.println("soccer schedule");
                Intent soccerSchedule = new Intent(MainActivity.this, TaskDetails.class);
                soccerSchedule.putExtra("soccer", soccerText);
                MainActivity.this.startActivity(soccerSchedule);

            }

        });
        Button soccerStadiums = MainActivity.this.findViewById(R.id.soccer_matches);
        soccerStadiums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button)view;
                String soccerText = b.getText().toString();
                System.out.println("soccer stadiums");
                Intent soccerStadiums = new Intent(MainActivity.this, TaskDetails.class);
                soccerStadiums.putExtra("soccer", soccerText);
                MainActivity.this.startActivity(soccerStadiums);

            }

        });
        Button settings = MainActivity.this.findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("settings");
                Intent settings = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity(settings);
            }
        });
//        Button taskDetails = MainActivity.this.findViewById(R.id.task_details_title);
//        taskDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("task details");
//                Intent taskDetails = new Intent(MainActivity.this, TaskDetails.class);
//                MainActivity.this.startActivity(taskDetails);
//            }
//        });
    }

    @Override
    public void onResume() { // this is probably the correct place for ALL rendered info

        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView address = findViewById(R.id.task_list_title);
        address.setText(preferences.getString("namePotato", "Go to Settings username"));
    }
    }



//this is a my first code in Android

