package com.proj.planed.ui.alarms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.proj.planed.NavigationActivity;
import com.proj.planed.R;
import com.proj.planed.ui.planner.Planner;

import java.util.List;

public class AlarmsListFragment extends Fragment implements OnToggleAlarmListener {
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmsListViewModel alarmsListViewModel;
    private RecyclerView alarmsRecyclerView;
    private FloatingActionButton addAlarm;
    FloatingActionButton deleteAll;
    SearchView searchView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this);

        alarmsListViewModel = new ViewModelProvider(this).get(AlarmsListViewModel.class);

        alarmsListViewModel.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) {
                    alarmRecyclerViewAdapter.setAlarms(alarms);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listalarms, container, false);

        alarmsRecyclerView = view.findViewById(R.id.fragment_listalarms_recylerView);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);


        TextView addAlarmActionText = view.findViewById(R.id.add_alarms_action_text);
        TextView addPersonActionText  = view.findViewById(R.id.delete_alarms_action_text);;


        alarmsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0.9) {
                    deleteAll.hide();
                    addPersonActionText.setVisibility(View.INVISIBLE);
                } else {
                    if (dy < 0.9)
                        deleteAll.show();
                        addPersonActionText.setVisibility(View.VISIBLE);
                }
            }
        });



        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Alarm alarm = alarmRecyclerViewAdapter.getWordAtPosition(position);
                        alarm.cancelAlarm(getContext());
                        Toast.makeText(getContext(), "Deleted " +
                                        alarm.getTitle(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        alarmsListViewModel.delete(alarm);
                    }
                });

        helper.attachToRecyclerView(alarmsRecyclerView);

        addAlarm = view.findViewById(R.id.add_alarm_fab);
        deleteAll = view.findViewById(R.id.button_delete_all);


        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_alarmsListFragment_to_createAlarmFragment);
            }
        });




        searchView = ((NavigationActivity)getActivity()).searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity().getApplicationContext(), newText,Toast.LENGTH_SHORT).show();
                //searchAlarm(newText);
                //AlarmRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmsListViewModel.deleteAll();
            }
        });




        addAlarm.show();
        deleteAll.show();
        addAlarmActionText.setVisibility(View.VISIBLE);
        addPersonActionText.setVisibility(View.VISIBLE);







        return view;
    }




    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(getContext());
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(getContext());
            alarmsListViewModel.update(alarm);
        }
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of AlarmsFragment");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity().getApplicationContext(), newText,Toast.LENGTH_SHORT).show();
                searchAlarm(newText);
                //AlarmRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of AlarmsFragment");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onPause();
    }

    public void searchAlarm(String alarmName){

        alarmsListViewModel.getAlarmSearch(alarmName).observe(getViewLifecycleOwner(), new Observer<List<Alarm>>() {

            @Override
            public void onChanged(List<Alarm> Alarms) {
                if (Alarms != null) {
                    alarmRecyclerViewAdapter.setAlarms(Alarms);
                }
            }
        });
        alarmRecyclerViewAdapter.notifyDataSetChanged();



    }


}