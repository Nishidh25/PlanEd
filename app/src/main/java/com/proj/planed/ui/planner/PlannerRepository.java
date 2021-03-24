package com.proj.planed.ui.planner;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.proj.planed.Database;

import java.util.List;

public class PlannerRepository {
    private PlannerDao plannerDao;
    private LiveData<List<Planner>> plannerLiveData;

    public PlannerRepository(Application application) {
        Database db = Database.getDatabase(application);
        plannerDao = db.plannerDao();
        plannerLiveData = plannerDao.getPlans();
    }

    public void insert(Planner plan) {
        Database.databaseWriteExecutor.execute(() -> {
            plannerDao.insert(plan);
        });
    }

    public void update(Planner plan) {
        Database.databaseWriteExecutor.execute(() -> {
            plannerDao.update(plan);
        });
    }




    private static class deleteAllPlannerAsyncTask extends AsyncTask<Void, Void, Void> {
        private PlannerDao mAsyncTaskDao;

        deleteAllPlannerAsyncTask(PlannerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class deletePlannerAsyncTask extends AsyncTask<Planner, Void, Void> {
        private PlannerDao mAsyncTaskDao;

        deletePlannerAsyncTask(PlannerDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Planner... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void deleteplan(Planner plan){

        new deletePlannerAsyncTask(plannerDao).execute(plan);
        //AlarmDatabase.databaseWriteExecutor.execute(() -> {
        //    alarmDao.delete(alarm);
        //});
    }

    public LiveData<List<Planner>> getPlannersDay(String day) {
        plannerLiveData = plannerDao.getPlannersDay(day);
        return plannerLiveData;
    }



    public void deleteAllplans()  {
        new deleteAllPlannerAsyncTask(plannerDao).execute();
    }

    public LiveData<List<Planner>> getPlannerLiveDataLiveData() {
        return plannerLiveData;
    }
}
