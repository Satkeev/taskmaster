package com.satkeev.github.taskmaster;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.core.app.ActivityCompat;
import androidx.room.Database;
import androidx.room.Room;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTask extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

        Uri imageFromIntent;
        ArrayList<Team> teams;
        String lastFileIUploadedKey = "";

    FusedLocationProviderClient locationProviderClient;

    Location currentLocation;
    String addressString;


    //        Database database;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);


            askForPermissionToUseLocation();
            configureLocationServices();
            askForLocation();

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
                 Intent getPicIntent = new Intent(Intent.ACTION_GET_CONTENT);
                 getPicIntent.setType("*/*");
                 startActivityForResult(getPicIntent, 2020);

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
                // we can now send it to

             if(imageFromIntent != null) {

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
//                TextView task_address = findViewById(R.id._title);

                Task taskToAdd = Task.builder()
                .title(task_title.getText().toString())
                 .body(task_description.getText().toString())
                 .state(task_state.getText().toString())
                        .address( addressString)
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

    public void askForPermissionToUseLocation() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    public void askForLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//
//            Log.e(TAG, "PERMISSIONS WERE NOT ACTUALLY GRANTED!!");
//            return;
//        }
//        locationProviderClient.getLastLocation()
//                .addOnSuccessListener(location -> Log.i(TAG + ".locsuccess", location.toString()))
//                .addOnFailureListener(error -> Log.e(TAG + ".locFailure", error.toString()))
//                .addOnCanceledListener(() -> Log.e(TAG + ".locCancel", "it was canceled"))
//                .addOnCompleteListener(complete -> Log.i(TAG + ".locComplete", complete.toString()));

        // TODO: geocoder
        LocationRequest locationRequest;
        LocationCallback locationCallback;

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                currentLocation = locationResult.getLastLocation();
                Log.i("Amplify location", currentLocation.toString());

                // TODO: GeoCoding the coordinates
                Geocoder geocoder = new Geocoder(AddTask.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 10);
                    Log.i("Amplify_location", addresses.get(0).toString());
                    addressString = addresses.get(0).getAddressLine(0);
                    Log.i("Amplify_location", addressString);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            Toast t = new Toast(this);
            t.setText("You need to accept the permissions");
            t.setDuration(Toast.LENGTH_LONG);
            t.show();
            return;
        }
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());


    }

    public void configureLocationServices(){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // fuses the multiple location requests into one big one, gives you the most accurate that comes back
    }
}

