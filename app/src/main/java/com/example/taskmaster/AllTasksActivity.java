package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    private List<MyTask> myTasks = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private TaskDao taskDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        recyclerView = findViewById(R.id.recyclerViewAllTasks);
        appDatabase= Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "task_master").allowMainThreadQueries().build();

        taskDao = appDatabase.taskDao();
        myTasks = taskDao.getAllTasks();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new TaskAdapter(this , myTasks));

    } //end onCreate()

    @Override
    protected void onPostResume() {
        super.onPostResume();
        myTasks = taskDao.getAllTasks();
        recyclerView.setAdapter(new TaskAdapter(this , myTasks));
    }
} //end class