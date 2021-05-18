package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private List<Task> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTaskButton = findViewById(R.id.addBtn);
        allTasksButton = findViewById(R.id.allTasksBtn);
//        taskOneButton = findViewById(R.id.taskBtn1);
//        taskTwoButton = findViewById(R.id.taskBtn2);
//        taskThreeButton = findViewById(R.id.taskBtn3);
        usernameTextView = findViewById(R.id.usernameTextView);
        settingButton = findViewById(R.id.settingBtn);
       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       recyclerView = findViewById(R.id.recyclerView);
//        SharedPreferences.Editor editor = sharedPreferences.edit();

        usernameTextView.setText(sharedPreferences.getString("username", "username"));
        Intent detailsActivityIntent = new Intent(this, DetailsActivity.class);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        allTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllTasksActivity.class);
                startActivity(intent);
            }
        });

//        taskOneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                detailsActivityIntent.putExtra("taskTitle", taskOneButton.getText());
//                startActivity(detailsActivityIntent);
//
//            }
//        });
//
//        taskTwoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                detailsActivityIntent.putExtra("taskTitle", taskTwoButton.getText());
//                startActivity(detailsActivityIntent);
//
//            }
//        });
//
//        taskThreeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                detailsActivityIntent.putExtra("taskTitle", taskThreeButton.getText());
//                startActivity(detailsActivityIntent);
//            }
//        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        tasks.add(new Task("Do Code 27", "do the challenge asap", "in progress"));
        tasks.add(new Task("Do Code 28", "do the challenge asap", "in progress"));
        tasks.add(new Task("Do Read 29", "do the read asap", "in progress"));
        tasks.add(new Task("Do Code 27", "do the challenge asap", "in progress"));
        tasks.add(new Task("Do Code 28", "do the challenge asap", "in progress"));
        tasks.add(new Task("Do Read 29", "do the read asap", "in progress"));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new TaskAdapter(this, tasks));

    }//end onCreate()

    @Override
    protected void onResume() {
        super.onResume();
        usernameTextView.setText(sharedPreferences.getString("username", "username"));
        Log.d("---------------------", "onResume():  ------------------");
    }

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