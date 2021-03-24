package com.proj.planed.ui.planner;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlannerDao {
    @Insert
    void insert(Planner Plan);

    @Query("DELETE FROM planner_table")
    void deleteAll();

    @Query("SELECT * FROM planner_table ORDER BY created ASC")
    LiveData<List<Planner>> getPlans();

    @Query("SELECT * FROM planner_table WHERE day = :day ORDER BY created ASC")
    LiveData<List<Planner>> getPlannersDay(String day);

    @Update
    void update(Planner Plan);

    @Delete
    void delete(Planner Plan);
}
