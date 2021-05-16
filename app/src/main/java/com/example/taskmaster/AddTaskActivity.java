package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addTaskButton;
    private EditText taskTitle;
    private EditText taskDescription;
//    private Set<Task> tasks= new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        addTaskButton = findViewById(R.id.addTaskBtn);
        taskTitle = findViewById(R.id.taskTitleEditText);
        taskDescription = findViewById(R.id.taskDescriptionEditText);
        addTaskButton.setOnClickListener(this);

    } //end onCreate()

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addTaskBtn){
            if(TextUtils.isEmpty(taskTitle.getText()))
                taskTitle.setError("Task Title Can't Be Empty");
            if(TextUtils.isEmpty(taskDescription.getText()))
                taskDescription.setError("Task Description Can't Be Empty");
            else{
//                AllTasksActivity allTasksActivity = new AllTasksActivity();
//                tasks = allTasksActivity.readTasks(this, "tasks");
//                tasks.add(new Task(taskTitle.getText().toString(), taskDescription.getText().toString()));
//                saveData(tasks);
                Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
            }

        }
    } //end onClick()

//    private void saveData(Set<Task> tasks){
//        SharedPreferences sharedPreferences = this.getSharedPreferences("tasks", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        editor.putString("tasks", gson.toJson(tasks)).apply();
//    }

} //end class