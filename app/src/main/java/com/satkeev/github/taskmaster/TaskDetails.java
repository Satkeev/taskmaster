package com.satkeev.github.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class TaskDetails extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.task_details);
            Intent intent = getIntent();
            System.out.println(intent.getExtras().getString("soccer"));

            TextView itemNameView = TaskDetails.this.findViewById(R.id.task_details_text);
            itemNameView.setText(intent.getExtras().getString("soccer"));

//            Database database;
//            database = Room.databaseBuilder(getApplicationContext(), Database.class, "satkeev_tasks")
//                    .allowMainThreadQueries()
//                    .build();
            // TODO: don't pause the app

//            ArrayList<Task> tasks = (ArrayList<Task>) database.taskDao().getAllTasksReversed();

//            RecyclerView recyclerView = findViewById(R.id.soccer_task_recycle);
//            LinearLayoutManager l = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(l);
//            recyclerView.setAdapter(new TaskAdapter(task, (TaskAdapter.OnInteractWithTaskListener) this));

//            Button addButton = com.satkeev.github.taskmaster.TaskDetails.this.findViewById(R.id.task_details_title);
//            addButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    Intent taskDetails = new Intent(com.satkeev.github.taskmaster.TaskDetails.this, MainActivity.class);
//                    com.satkeev.github.taskmaster.TaskDetails.this.startActivity(taskDetails);
//                }
//            });
        }
    }

