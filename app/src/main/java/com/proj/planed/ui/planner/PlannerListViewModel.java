package com.proj.planed.ui.planner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlannerListViewModel extends AndroidViewModel {
    private PlannerRepository plannerRepository;
    private LiveData<List<Planner>> plannersLiveData;


    public PlannerListViewModel(@NonNull Application application) {
        super(application);

        plannerRepository = new PlannerRepository(application);
        plannersLiveData = plannerRepository.getPlannerLiveDataLiveData();
    }

    public void update(Planner planner) {
        plannerRepository.update(planner);
    }

    LiveData<List<Planner>> getAllPlans() {
        return plannersLiveData;
    }
    public LiveData<List<Planner>> getPlannersDay(String day){ return plannerRepository.getPlannersDay(day);}


    public void deleteAll() {plannerRepository.deleteAllplans();}

    public void delete(Planner planner){
        plannerRepository.deleteplan(planner);
    }

    public LiveData<List<Planner>> getPlannersLiveData(){
        return plannersLiveData;
    }
}
