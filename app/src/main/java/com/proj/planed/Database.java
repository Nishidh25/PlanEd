package com.proj.planed;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.proj.planed.ui.alarms.Alarm;
import com.proj.planed.ui.alarms.AlarmDao;
import com.proj.planed.ui.faq.Faq;
import com.proj.planed.ui.faq.FaqDao;
import com.proj.planed.ui.planner.Planner;
import com.proj.planed.ui.planner.PlannerDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Alarm.class, Planner.class, Faq.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract AlarmDao alarmDao();
    public abstract PlannerDao plannerDao();
    public abstract FaqDao faqDao();


    private static volatile Database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            "alarm_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
