package com.satkeev.github.taskmaster;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

    @Dao // Database Access Object - Controller for saving and retrieving and all REST on an android database
    public interface TaskDao {

        @Insert
        public void saveTheTask(Task task);

        @Query("SELECT * FROM Task")
        public List<Task> getAllTasks();

        @Query("SELECT * FROM Task ORDER BY id DESC")
        public List<Task> getAllTasksReversed();

    }
