package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;

public class TaskRecyclerView extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_recycler_view);

        ArrayList<Task> tasks = new ArrayList<>();

//        Task taskOne = new Task("taskOne", "soccer tickets", "soccer schedule");
//        Task taskTwo = new Task("taskTwo", "soccer stadiums", "soccer tickets");
//        Task taskThree = new Task("taskThree", "soccer schedule", "soccer stadiums");

//        tasks.add(taskOne);
//        tasks.add(taskTwo);
//        tasks.add(taskThree);

        RecyclerView recyclerView = findViewById(R.id.soccer_task_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasks, this));
        System.out.println("we arrived");
    }

    @Override
    public void taskListener(Task task){
        Intent intent = new Intent(TaskRecyclerView.this, TaskDetails.class);
        intent.putExtra("taskTitle", task.getTitle());
        intent.putExtra("taskBody", task.getBody());
        intent.putExtra("taskState", task.getState());
        this.startActivity(intent);
    }

}