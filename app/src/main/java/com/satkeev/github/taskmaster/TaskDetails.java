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

    public class TaskDetails extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.task_details);
            Intent intent = getIntent();
            System.out.println(intent.getExtras().getString("soccer"));

            TextView itemNameView = TaskDetails.this.findViewById(R.id.task_details_text);
            itemNameView.setText(intent.getExtras().getString("soccer"));


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

