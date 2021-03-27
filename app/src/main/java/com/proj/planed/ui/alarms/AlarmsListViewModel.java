package com.proj.planed.ui.alarms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.proj.planed.ui.planner.Planner;

import java.util.List;

public class AlarmsListViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmsLiveData;


    public AlarmsListViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }

    public void update(Alarm alarm) {
        alarmRepository.update(alarm);
    }

    LiveData<List<Alarm>> getAllAlarms() {
        return alarmsLiveData;
    }

    public void deleteAll() {alarmRepository.deleteAll();}

    public void delete(Alarm alarm){
        alarmRepository.delete(alarm);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }

    public LiveData<List<Alarm>> getAlarmSearch(String name){ return alarmRepository.getAlarmSearch(name);}

}
