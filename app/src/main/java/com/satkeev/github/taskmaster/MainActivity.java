package com.satkeev.github.taskmaster;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnInteractingWithTaskListener {

    //    Database database;
    ArrayList<Task> tasks;
    ArrayList<Team> teams;
    RecyclerView recyclerView;
    Handler handler;
    Handler handleSingleItemAdded;
    int teamWeAreOnIndex = 0;

    Handler handleCheckLoggedIn;

    @Override
    public void onResume() { // this is probably the correct place for ALL rendered info

        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView address = findViewById(R.id.task_list_title);
        address.setText(preferences.getString("namePotato", "Go to Settings username"));

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    tasks.clear();
                    for (Task task : response.getData()) {
                        if (preferences.contains("heroy")) {
                            if (task.apartOf.getName().equals(preferences.getString("heroy", "na"))) {
                                tasks.add(task);
                            }
                        } else {
                            tasks.add(task);
                        }
                        System.out.println(task.toString());
                    }

                    handler.sendEmptyMessage(1);
                    Log.i("Amplify.queryitems", "Got this many items from dynamo " + tasks.size());

                },
                error -> Log.i("Amplify.queryitems", "Did not get items"));


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        handleCheckLoggedIn = new Handler(Looper.getMainLooper(), message -> {
            if (message.arg1 == 0) {
                Log.i("Amplify.login", "handler: they were not logged in");
            } else if (message.arg1 == 1) {
                Log.i("Amplify.login", "handler: they were logged in");
                // TODO: display their username
                Log.i("Amplify.user", Amplify.Auth.getCurrentUser().getUsername());

                TextView usernameView = findViewById(R.id.name_title);
                usernameView.setText(Amplify.Auth.getCurrentUser().getUsername());

            } else {
                Log.i("Amplify.login", "send true or false pls");
            }
            return false;
        });


        configureAws();


        tasks = new ArrayList<Task>();

        RecyclerView recyclerView = findViewById(R.id.soccer_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasks, this));

        handler = new Handler(Looper.getMainLooper(),
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                });
//
//

//        Amplify.Auth.signIn(
//                "Kamit",
//                "Nasip2001$",
//                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
//                error -> Log.e("AuthQuickstart", error.toString())
//        );

        Button allTasks = MainActivity.this.findViewById(R.id.alltasks_button);
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("all tasks");
                Intent allTasks = new Intent(MainActivity.this, TaskRecyclerView.class);
                MainActivity.this.startActivity(allTasks);
            }
        });

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
                Button b = (Button) view;
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
                Button b = (Button) view;
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
                Button b = (Button) view;
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


    Button signin = MainActivity.this.findViewById(R.id.login_button);
        signin.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        System.out.println("signin");
        Intent login = new Intent(MainActivity.this, SignInActivity.class);
        MainActivity.this.startActivity(login);
    }
    });

        Button signup = MainActivity.this.findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                System.out.println("signup");
                Intent signup = new Intent(MainActivity.this, Signup.class);
                MainActivity.this.startActivity(signup);
            }
        });

        Button logou= MainActivity.this.findViewById(R.id.logout_button);
        logou.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                System.out.println("logou");
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }
        });

}


    @Override
    public void taskListener(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetails.class);
        intent.putExtra("title", task.getTitle());
        intent.putExtra("body", task.getBody());
        intent.putExtra("state", task.getState());
        this.startActivity(intent);
    }

    public void setUpThreeTeams() {
        Team team1 = Team.builder()
                .name("Team1")
                .build();

        Team team2 = Team.builder()
                .name("Team2")
                .build();

        Team team3 = Team.builder()
                .name("Team3")
                .build();
        Amplify.API.mutate(ModelMutation.create(team1),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );

        Amplify.API.mutate(ModelMutation.create(team2),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );

        Amplify.API.mutate(ModelMutation.create(team3),
                response -> Log.i("Amplify", "added a team"),
                error -> Log.e("Amplify", "failed to add a team")
        );


    }

        public void addListenersToButtons() {
            Button login = findViewById(R.id.login_button);
            Button signup = findViewById(R.id.signup_button);
            login.setOnClickListener(view -> this.startActivity(new Intent(this, SignInActivity.class)));
            signup.setOnClickListener(view -> this.startActivity(new Intent(this, Signup.class)));
        }
            public void loginMockUser () {
                Amplify.Auth.signIn(
                        "Kamit",
                        "Nasip2001$",
                        result -> {
                            Log.i("Amplify.login", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                            getIsSignedIn();
                        },
                        error -> Log.e("Amplify.login", error.toString())
                );
            }

            public void verifyOneMockUser () {
                Amplify.Auth.confirmSignUp(
                        "Kamit",
                        "433191",
                        result -> Log.i("Amplify.confirm", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                        error -> Log.e("Amplify.confirm", error.toString())
                );
            }


//                // TODO: we need to collect from the user: username, password and email
//                Amplify.Auth.signUp(
//                        "Kamit",
//                        "Nasip2001$",
//                        AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), "nick.carignan@codefellows.com").build(),
//                        result -> Log.i("Amplify.signup", "Result: " + result.toString()),
//                        error -> Log.e("Amplify.signup", "Sign up failed", error)
//                );
//
//            }

            public void getIsSignedIn () {
                Amplify.Auth.fetchAuthSession(
                        result -> {
                            Log.i("Amplify.login", result.toString());
                            Message message = new Message();

                            if (result.isSignedIn()) {
                                message.arg1 = 1;
                                handleCheckLoggedIn.sendMessage(message);
                            } else {
                                message.arg1 = 0;
                                handleCheckLoggedIn.sendMessage(message);
                            }
                        },
                        error -> Log.e("Amplify.login", error.toString())
                );
            }


        private void configureAws () {
            try {
                // entire configuration
                Amplify.addPlugin(new AWSApiPlugin());
                // Add this line, to include the Auth plugin.
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.configure(getApplicationContext());
                Log.i("MyAmplifyApp", "Initialized Amplify");
            } catch (AmplifyException e) {
                e.printStackTrace();
                Log.e("MyAmplifyApp", "Could not initialize Amplify", e);
            }

            Amplify.Auth.fetchAuthSession(
                    result -> Log.i("AmplifyQuickstart", result.toString()),
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );

        }
    }




