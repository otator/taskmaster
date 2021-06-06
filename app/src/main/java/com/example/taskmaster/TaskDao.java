package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * from MyTask")
    List<MyTask> getAllTasks();

    @Insert
    void addTask(MyTask myTask);

    @Query("SELECT COUNT(*) FROM MyTask")
    int getTasksNumber();
}
