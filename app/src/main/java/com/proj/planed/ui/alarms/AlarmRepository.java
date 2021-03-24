package com.proj.planed.ui.alarms;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proj.planed.Database;

import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;

    public AlarmRepository(Application application) {
        Database db = Database.getDatabase(application);
        alarmDao = db.alarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(Alarm alarm) {
        Database.databaseWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public void update(Alarm alarm) {
        Database.databaseWriteExecutor.execute(() -> {
            alarmDao.update(alarm);
        });
    }




    private static class deleteAllAlarmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao mAsyncTaskDao;

        deleteAllAlarmsAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }



    private static class deleteAlarmsAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao mAsyncTaskDao;

        deleteAlarmsAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void delete(Alarm alarm){

        new deleteAlarmsAsyncTask(alarmDao).execute(alarm);
        //AlarmDatabase.databaseWriteExecutor.execute(() -> {
        //    alarmDao.delete(alarm);
        //});
    }



    public void deleteAll()  {
        new deleteAllAlarmsAsyncTask(alarmDao).execute();
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
