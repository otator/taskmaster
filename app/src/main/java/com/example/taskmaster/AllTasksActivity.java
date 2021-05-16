package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Set;

public class AllTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
//        displayTasks(readTasks(this, "tasks"));

    } //end onCreate()
//
//    public Set<Task> readTasks(Context context,String key){
//        Gson gson = new Gson();
////        Set<Task> t = new HashSet<Task>();
//        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
//        String gsonTasks = sharedPreferences.getString("tasks", null);
//        return gson.fromJson(gsonTasks, Set.class);
//    }
//
//    public void displayTasks(Set<Task> tasks){
//        ConstraintLayout parent = findViewById(R.id.allTasksParent);
//        Log.d("-----------------------", "displayTasks: " + tasks);
//        for(Task task: tasks){
//            TextView titleTextView = new TextView(this);
//            titleTextView.setText(task.getTitle());
//
//            TextView descriptionTextView = new TextView(this);
//            descriptionTextView.setText(task.getDescription());
//            parent.addView(titleTextView);
//            parent.addView(descriptionTextView);
//        }
//    }
} //end class