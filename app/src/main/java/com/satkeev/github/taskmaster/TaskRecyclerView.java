package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class TaskRecyclerView extends AppCompatActivity implements TaskAdapter.OnInteractWithTaskListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_recycler_view);

        ArrayList<Task> tasks = new ArrayList<>();

        Task taskOne = new Task("taskOne", "soccer tickets", "soccer schedule");
        Task taskTwo = new Task("taskTwo", "soccer stadiums", "soccer tickets");
        Task taskThree = new Task("taskThree", "soccer schedule", "soccer stadiums");

        tasks.add(taskOne);
        tasks.add(taskTwo);
        tasks.add(taskThree);

        RecyclerView recyclerView = findViewById(R.id.soccer_task_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasks, this));
        System.out.println("we arrived");
    }

    @Override
    public void tasksToDoListener(Task task){
        Intent intent = new Intent(TaskRecyclerView.this, TaskDetails.class);
        intent.putExtra("taskTitle", task.title);
        intent.putExtra("taskBody", task.body);
        intent.putExtra("taskState", task.state);
        this.startActivity(intent);
    }
}