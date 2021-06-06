package com.example.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyTask.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
