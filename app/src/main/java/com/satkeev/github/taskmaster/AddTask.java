package com.satkeev.github.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

        Uri imageFromIntent;
        ArrayList<Team> teams;
        String lastFileIUploadedKey = "";

//        Database database;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);


        Intent intent = getIntent();
        if(intent.getType() != null){
            Log.i("Amplify.pick", intent.toString());
            imageFromIntent = intent.getClipData().getItemAt(0).getUri();
            ImageView image = findViewById(R.id.image_view);
            image.setImageURI(imageFromIntent);
        }


            Amplify.API.query(ModelQuery.list(Team.class),
                response -> {
                    teams = new ArrayList<>();
                    for(Team team : response.getData()){
                        teams.add(team);

                    }
                },
                error -> Log.e(  "AmplifyAddTask",  "failed getting teams"));

            AnalyticsEvent event = AnalyticsEvent.builder()
                    .name("AddTask")
                    .addProperty("time", Long.toString(new Date().getTime()))
                    .addProperty("addTask", "added task")
                    .build();
            Amplify.Analytics.recordEvent(event);

//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "satkeev_tasks")
//                .allowMainThreadQueries()
//                .build();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context context = getApplicationContext();
        CharSequence text = "Submitted!";
        int duration = Toast.LENGTH_SHORT;

        final Toast toast = Toast.makeText(context, text, duration);

        Button addPhoto = AddTask.this.findViewById(R.id.photo_button);
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    Log.i("Amplify.pickImage", "Got the image back from the activity");
                    // This will know about the image in `data`
                    // we can now send it to s3

                    File fileCopy = new File(getFilesDir(), "test file");

                    try {
//                        System.out.println(data);
                        InputStream inStream = getContentResolver().openInputStream(imageFromIntent);
                        FileUtils.copy(inStream, new FileOutputStream(fileCopy));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Amplify.pickImage", e.toString());
                    }

                    uploadFile(fileCopy, "soccer");
                    downloadFile("soccer");

                }

            });



            Button addButton = AddTask.this.findViewById(R.id.addtaskbutton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                Log.i("Amplify.pickImage", "Got the image back from the activity");
                // This will know about the image in `data`
                // we can now send it to s3

                File fileCopy = new File(getFilesDir(), "test file");

                try {
//                        System.out.println(data);
                    InputStream inStream = getContentResolver().openInputStream(imageFromIntent);
                    FileUtils.copy(inStream, new FileOutputStream(fileCopy));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Amplify.pickImage", e.toString());
                }

                uploadFile(fileCopy, "soccer");
                downloadFile("soccer");


                RadioGroup heroy = findViewById(R.id.heroytask_title);
                RadioButton checkbutton = findViewById(heroy.getCheckedRadioButtonId());
                String teamName = checkbutton.getText().toString();
                Team team = null;
                for(int i=0; i<teams.size(); i++){
                  if(teams.get(i).getName().equals(teamName)){
                  team = teams.get(i);
                  }
                }
                System.out.println(teamName);
                System.out.println(teams);
                System.out.println(team);

                toast.show();

                TextView task_title = findViewById(R.id.nameoftask);
                TextView task_description = findViewById(R.id.task_description);
                TextView task_state = findViewById(R.id.status_task);

                Task taskToAdd = Task.builder()
                .title(task_title.getText().toString())
                 .body(task_description.getText().toString())
                 .state(task_state.getText().toString())
                 .key("soccer")
                        .apartOf(team)
                        .build();

                System.out.println(taskToAdd.toString());

                Amplify.API.mutate(ModelMutation.create(taskToAdd),
                        response -> Log.i("Amplify", "Successfully added " + taskToAdd.getTitle()),
                        error -> Log.e("Amplify", error.toString()));
//                database.taskDao().saveTheTask(taskToAdd);
                Intent addTask = new Intent(AddTask.this, MainActivity.class);
                AddTask.this.startActivity(addTask);
            }
        });
    }
    public void uploadFile(File f, String key){
        lastFileIUploadedKey = key;
        Amplify.Storage.uploadFile(
                key,
                f,
                result -> {
                    Log.i("Amplify.s3", "Successfully uploaded: " + result.getKey());
                    downloadFile(key);
                },
                storageFailure -> Log.e("Amplify.s3", "Upload failed", storageFailure)
        );
    }
    private void downloadFile(String fileKey){
        Amplify.Storage.downloadFile(
                fileKey,
                new File(getApplicationContext().getFilesDir() + "/" + fileKey + ".txt"),
                result -> {
                    Log.i("Amplify.s3down", "Successfully downloaded: " + result.getFile().getName());
                    ImageView image = findViewById(R.id.imageLastUploaded);
                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                    // TODO: display the image
                },
                error -> Log.e("Amplify.s3down",  "Download Failure", error)
        );
    }
    @Override
    public void taskListener(Task task) {

    }
}

