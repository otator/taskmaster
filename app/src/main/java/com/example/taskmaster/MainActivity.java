package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button addTaskButton;
    private Button allTasksButton;
    private Button taskOneButton;
    private Button taskTwoButton;
    private Button taskThreeButton;
    private TextView usernameTextView;
    private Button settingButton;
    SharedPreferences sharedPreferences = null;
    private RecyclerView recyclerView;
    public static List<Task> tasks = new ArrayList<>();
    private AppDatabase appDatabase;
    private Integer tasksNumber = 0;
    private TaskDao taskDao;
    private String  sessionUsername;
    private TextView signOut;
    private TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        addTaskButton = findViewById(R.id.addBtn);
        allTasksButton = findViewById(R.id.allTasksBtn);
        usernameTextView = findViewById(R.id.usernameTextView);
        settingButton = findViewById(R.id.settingBtn);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        recyclerView = findViewById(R.id.recyclerView);

        appDatabase= Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().build();



        // open add task activity on button clicked
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // open all tasks activity on button clicked
        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllTasksActivity.class);
                startActivity(intent);
            }
        });

        //open settings activity on button clicked
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        taskDao = appDatabase.taskDao();
        tasks = taskDao.getAllTasks();
        if(!sharedPreferences.getString("tasksNumber", "null").equals("null")) {
            tasksNumber = Integer.parseInt(sharedPreferences.getString("tasksNumber", "5"));
            tasks = tasks.subList(0, tasksNumber);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new TaskAdapter(this, tasks));

        // Add this line, to include the Auth plugin.
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());

            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }


        Amplify.Auth.fetchUserAttributes(
                attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
        sessionUsername = AWSMobileClient.getInstance().getUsername();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", sessionUsername);
        editor.apply();
        usernameTextView.setText(sharedPreferences.getString("username", "username"));

        signOut = findViewById(R.id.signoutTextView);
        signOut.setVisibility(View.INVISIBLE);
        signIn = findViewById(R.id.signinTextView);
        signIn.setVisibility(View.INVISIBLE);
        Amplify.Auth.fetchAuthSession(
                result -> {
                    if(!result.isSignedIn()) {
                        // if the user signed in, show him the sign in button
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signOut.setVisibility(View.INVISIBLE);
                                signIn.setVisibility(View.VISIBLE);
                            }
                        });

                    }else{
                        // if the user did not sign in, show the sign in button
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signOut.setVisibility(View.VISIBLE);
                                signIn.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
        signOut.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> Log.i("AuthQuickstart", "Signed out successfully"),
                    error -> Log.e("AuthQuickstart", error.toString())
            );

        });

        signIn.setOnClickListener(view ->{
            Amplify.Auth.fetchAuthSession(
                    result -> {
                        Log.i("AuthQuickStart", result.toString());
                        if(!result.isSignedIn()) {
                            Amplify.Auth.signInWithWebUI(
                                    this,
                                    results -> Log.i("AuthQuickStart", results.toString()),
                                    error -> Log.e("AuthQuickStart", error.toString())
                            );
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    signOut.setVisibility(View.VISIBLE);
                                    signIn.setVisibility(View.INVISIBLE);
                                }
                            });

                        }

                    },
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );
        });
    } //end onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        sessionUsername = AWSMobileClient.getInstance().getUsername();
        Log.v("----user-----", "????????"+sessionUsername);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", sessionUsername);
        editor.apply();
        usernameTextView.setText(!sharedPreferences.getString("username", "username").equals("username")?sharedPreferences.getString("username", "username")+"'s tasks": "username");
        taskDao = appDatabase.taskDao();
        tasks = taskDao.getAllTasks();
        if(!sharedPreferences.getString("tasksNumber", "null").equals("null")) {
            tasksNumber = Integer.parseInt(sharedPreferences.getString("tasksNumber", "5"));

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new TaskAdapter(this, tasks.subList(0, tasksNumber)));
        Log.d("---------------------", "onResume():  ------------------");
        Amplify.Auth.fetchAuthSession(
                result -> {
                    if(!result.isSignedIn()) {
                        // if the user signed in, show him the sign in button
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signOut.setVisibility(View.INVISIBLE);
                                signIn.setVisibility(View.VISIBLE);
                            }
                        });

                    }else{
                        // if the user did not sign in, show the sign in button
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signOut.setVisibility(View.VISIBLE);
                                signIn.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );
    } // end onResume()

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("---------------------", "onStart():  ------------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("---------------------", "onPause():  ------------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("---------------------", "onDestroy():  ------------------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("---------------------", "onRestart():  ------------------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("---------------------", "onStop():  ------------------");
    }
}//end class