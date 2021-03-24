package com.proj.planed.ui.planner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class CreatePlannerViewModel extends AndroidViewModel {
    private PlannerRepository plannerRepository;

    public CreatePlannerViewModel(@NonNull Application application) {
        super(application);

        plannerRepository= new PlannerRepository(application);
    }

    public void insert(Planner plan) {
        plannerRepository.insert(plan);
    }


    public void delete(Planner plan) {
        plannerRepository.deleteplan(plan);
    }
}
