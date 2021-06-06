package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public static List<MyTask> myTasks = new ArrayList<>();
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
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);

                        Log.d("TAG", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        appDatabase= Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().fallbackToDestructiveMigration().build();



        // open add task activity on button clicked
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // open all myTasks activity on button clicked
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
        myTasks = taskDao.getAllTasks();
        if(!sharedPreferences.getString("tasksNumber", "null").equals("null")) {
            tasksNumber = Integer.parseInt(sharedPreferences.getString("tasksNumber", "5"));
            try{
                myTasks = myTasks.subList(0, tasksNumber);
            }catch (IndexOutOfBoundsException e){
                Toast.makeText(MainActivity.this, "No tasks found", Toast.LENGTH_LONG).show();
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new TaskAdapter(this, myTasks));

        // Add this line, to include the Auth plugin.
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
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

    protected void uploadFile(Context context){
        File file = new File(context.getFilesDir(), "key0");

        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append("test");
            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        Amplify.Storage.uploadFile(
                "key0",
                file,
                result -> Log.i("uploadFile", "Successfully Uploaded: "+ result.getKey()),
                error -> Log.e("uploadFile", "Storage Failure: "+error)
        );

    }




    @Override
    protected void onResume() {
        super.onResume();
        sessionUsername = AWSMobileClient.getInstance().getUsername();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", sessionUsername);
        editor.apply();
        usernameTextView.setText(!sharedPreferences.getString("username", "username").equals("username")?sharedPreferences.getString("username", "username")+"'s myTasks": "username");


        taskDao = appDatabase.taskDao();
        myTasks = taskDao.getAllTasks();
        if(!sharedPreferences.getString("tasksNumber", "null").equals("null")) {
            tasksNumber = Integer.parseInt(sharedPreferences.getString("tasksNumber", "5"));

        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        try{
            recyclerView.setAdapter(new TaskAdapter(this, myTasks.subList(0, tasksNumber)));
        }catch (IndexOutOfBoundsException e){
//            Toast.makeText(MainActivity.this, "No tasks found", Toast.LENGTH_LONG).show();
            recyclerView.setAdapter(new TaskAdapter(this, myTasks.subList(0, 0)));
        }

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