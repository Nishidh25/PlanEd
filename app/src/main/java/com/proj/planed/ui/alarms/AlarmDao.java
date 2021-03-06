package com.proj.planed.ui.alarms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
// Data access object
@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    LiveData<List<Alarm>> getAlarms();

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);


    @Query("SELECT * FROM alarm_table WHERE title LIKE :name || '%' ORDER BY created ASC")
    LiveData<List<Alarm>> getAlarmSearch(String name);

}
