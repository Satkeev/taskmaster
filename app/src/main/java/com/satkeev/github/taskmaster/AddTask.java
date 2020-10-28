package com.satkeev.github.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

        Database database;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);


        database = Room.databaseBuilder(getApplicationContext(), Database.class, "satkeev_tasks")
                .allowMainThreadQueries()
                .build();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = getApplicationContext();
        CharSequence text = "Submitted!";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);


        Button addButton = AddTask.this.findViewById(R.id.addtaskbutton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                toast.show();

                TextView task_title = findViewById(R.id.nameoftask);
                TextView task_description = findViewById(R.id.task_description);
                TextView task_state = findViewById(R.id.status_task);

                Task taskToAdd = Task.builder()
                .title(task_title.getText().toString())
                 .body(task_description.getText().toString())
                 .state(task_state.getText().toString()).build();

                Amplify.API.mutate(ModelMutation.create(taskToAdd),
                        response -> Log.i("Amplify", "Successfully added " + taskToAdd.getTitle()),
                        error -> Log.e("Amplify", error.toString()));
//                database.taskDao().saveTheTask(taskToAdd);
                Intent addTask = new Intent(AddTask.this, MainActivity.class);
                AddTask.this.startActivity(addTask);
            }
        });
    }

    @Override
    public void taskListener(Task task) {

    }
}

