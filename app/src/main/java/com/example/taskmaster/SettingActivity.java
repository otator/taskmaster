package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText usernameEditText;
    private String username = null;
    private EditText numberOfTasksEditText;
    private Integer tasksNumber;
    private Integer maxNumberOfTasks;
    private boolean isValidNumber = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        saveButton = findViewById(R.id.saveBtn);
        usernameEditText = findViewById(R.id.usernameEditText2);
        numberOfTasksEditText = findViewById(R.id.numberOfTasksEditText);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        usernameEditText.setText(sharedPreferences.getString("username", "username"));
        maxNumberOfTasks = MainActivity.tasks.size();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(numberOfTasksEditText.getText())){
                    tasksNumber = Integer.parseInt(numberOfTasksEditText.getText().toString());
                    // if the number of tasks the user enters is negative or more than the number of tasks itself
                    if(tasksNumber < 0 || tasksNumber > maxNumberOfTasks) {
                        numberOfTasksEditText.setError("number must be between 0 and " + maxNumberOfTasks);
                        isValidNumber = false;
                    }
                    else {
                        editor.putString("tasksNumber", numberOfTasksEditText.getText().toString());
                        isValidNumber = true;
                    }
                }
                if(!TextUtils.isEmpty(usernameEditText.getText())) {
                    username = usernameEditText.getText().toString();
                    editor.putString("username", username);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "User " + username + " saved!", Toast.LENGTH_SHORT).show();
                    // don't finish unless the number of tasks is valid
                    if(isValidNumber)
                        finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Please Enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//end onCreate()
}// end class