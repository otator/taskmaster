package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * from task")
    List<Task> getAllTasks();

    @Insert
    void addTask(Task task);

    @Query("SELECT COUNT(*) FROM task")
    int getTasksNumber();
}
