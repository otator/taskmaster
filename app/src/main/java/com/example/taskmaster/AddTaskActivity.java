package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addTaskButton;
    private EditText taskTitle;
    private EditText taskDescription;
    private EditText taskState;
    private AppDatabase appDatabase;
    private TextView tasksCounterTextView;
    private TaskDao taskDao;
//    private Set<Task> tasks= new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        addTaskButton = findViewById(R.id.addTaskBtn);
        taskTitle = findViewById(R.id.taskTitleEditText);
        taskDescription = findViewById(R.id.taskDescriptionEditText);
        taskState = findViewById(R.id.taskStateEditText);
        tasksCounterTextView = findViewById(R.id.numberOfTasksTextView);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().build();
        taskDao = appDatabase.taskDao();
        tasksCounterTextView.setText(taskDao.getTasksNumber()+"");
        addTaskButton.setOnClickListener(this);

    } //end onCreate()

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addTaskBtn){
            if(TextUtils.isEmpty(taskTitle.getText()))
                taskTitle.setError("Task Title Can't Be Empty");
            if(TextUtils.isEmpty(taskDescription.getText()))
                taskDescription.setError("Task Description Can't Be Empty");
            if(TextUtils.isEmpty(taskState.getText()))
                taskState.setText(R.string.default_state);


            taskDao.addTask(new Task(taskTitle.getText().toString(), taskDescription.getText().toString(), taskState.getText().toString()));

            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
            finish();

        }
    } //end onClick()


} //end class