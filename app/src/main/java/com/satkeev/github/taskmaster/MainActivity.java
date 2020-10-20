package com.satkeev.github.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
    }
}
//this is a my first code in Android

