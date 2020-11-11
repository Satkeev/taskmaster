package com.satkeev.github.taskmaster;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class TaskDetails extends AppCompatActivity {

    String lastFileIUploadedKey;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.task_details);
            Intent intent = getIntent();

//            TextView locationTask = TaskDetails.this.findViewById(R.id.location_title);
//            locationTask.setText(intent.getExtras().getString("address"));
//


            Button getimage = com.satkeev.github.taskmaster.TaskDetails.this.findViewById(R.id.imagepic_button);
            getimage.setOnClickListener((view -> retrieveFile()));
        }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override // This built in method we are overriding handles ALL results from when we leave the app
    protected void onActivityResult(int requestCodePotato, int resultCode, Intent data) {
        super.onActivityResult(requestCodePotato, resultCode, data);

        if(requestCodePotato == 4018){
            Log.i("Amplify.pickImage", "Got the image back from the activity");
            // This will know about the image in `data`
            // we can now send it to s3

            File fileCopy = new File(getFilesDir(), "test file");

            try {
                System.out.println(data);
                InputStream inStream = getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inStream, new FileOutputStream(fileCopy));
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Amplify.pickImage", e.toString());
            }

            uploadFile(fileCopy, "soccer");
            downloadFile("soccer");
        } else if(requestCodePotato == 2){
            // this implies I ran the line somewhere
            // startActivityForResult(shareToFacebookIntent, 2);
            Log.i("Amplify.doesnotexist", "good job you shared to facebook");
        } else {
            Log.i("Amplify.pickImage", "How the heck are you talking to my app??????");
        }
    }


    public void retrieveFile(){
        Intent getPicIntent = new Intent(Intent.ACTION_GET_CONTENT);// There are several intent types that are good for files
        // Samsung vs google vs whichever phone you have, might work differently with this or another intent
        getPicIntent.setType("*/*");
        //
        getPicIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{".png", ".jpg"});

//         These work together to make sure the pics are immediately accessible and openable locally
        getPicIntent.addCategory(Intent.CATEGORY_OPENABLE);
        getPicIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivity(getPicIntent);
        startActivityForResult(getPicIntent, 4018); // Request code is just the key / name of the intent
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

    private void uploadFile() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "soccer");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("Amplify.S3", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "soccer",
                exampleFile,
                result -> Log.i("Amplify.S3", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("Amplify.S3", "Upload failed", storageFailure)
        );
    }



}

