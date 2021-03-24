package com.proj.planed.ui.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.proj.planed.R;

import java.util.List;

import butterknife.BindView;

public class PlannerListFragment extends Fragment implements OnTogglePlannerListener {
    private PlannerRecyclerViewAdapter plannerRecyclerViewAdapter;
    private PlannerListViewModel plannerListViewModel;
    private RecyclerView plannerRecyclerView;
    private FloatingActionButton addPlanner;
    FloatingActionButton deleteAll;
    private String day; //Set current day


    @BindView(R.id.fragment_viewplan_checkTue)
    CheckBox tue;
    @BindView(R.id.fragment_viewplan_checkWed)
    CheckBox wed;
    @BindView(R.id.fragment_viewplan_checkThu)
    CheckBox thu;
    @BindView(R.id.fragment_viewplan_checkFri)
    CheckBox fri;
    @BindView(R.id.fragment_viewplan_checkSat)
    CheckBox sat;
    @BindView(R.id.fragment_viewplan_checkSun)
    CheckBox sun;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        plannerRecyclerViewAdapter = new PlannerRecyclerViewAdapter(this);

        plannerListViewModel = new ViewModelProvider(this).get(PlannerListViewModel.class);


        plannerListViewModel.getPlannersDay(day).observe(this, new Observer<List<Planner>>() {

            @Override
            public void onChanged(List<Planner> Planners) {
                if (Planners != null) {
                    plannerRecyclerViewAdapter.setPlans(Planners);
                }
            }
        });



        //getPlannersLiveData()

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listplans, container, false);

        plannerRecyclerView = view.findViewById(R.id.fragment_listplans_recylerView);
        plannerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        plannerRecyclerView.setAdapter(plannerRecyclerViewAdapter);


        CheckBox mon = view.findViewById(R.id.fragment_viewplan_checkMon);
        CheckBox tue = view.findViewById(R.id.fragment_viewplan_checkTue);
        CheckBox wed = view.findViewById(R.id.fragment_viewplan_checkWed);
        CheckBox thu = view.findViewById(R.id.fragment_viewplan_checkThu);
        CheckBox fri = view.findViewById(R.id.fragment_viewplan_checkFri);
        CheckBox sat = view.findViewById(R.id.fragment_viewplan_checkSat);
        CheckBox sun = view.findViewById(R.id.fragment_viewplan_checkSun);

        set_on_click(mon,"monday");
        set_on_click(tue,"tuesday");
        set_on_click(wed,"wednesday");
        set_on_click(thu,"thursday");
        set_on_click(fri,"friday");
        set_on_click(sat,"saturday");
        set_on_click(sun,"sunday");


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
                        Planner planner = plannerRecyclerViewAdapter.getWordAtPosition(position);
                        planner.cancelAlarm(getContext());
                        Toast.makeText(getContext(), "Deleted " +
                                        planner.getTitle(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        plannerListViewModel.delete(planner);
                    }
                });

        helper.attachToRecyclerView(plannerRecyclerView);

        addPlanner = view.findViewById(R.id.add_plans_fab);
        deleteAll = view.findViewById(R.id.button_delete_all_plans);


        addPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_plannerListFragment_to_createPlannerFragment);
            }
        });



        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plannerListViewModel.deleteAll();
            }
        });


        TextView addAlarmActionText = view.findViewById(R.id.add_plans_action_text);
        TextView addPersonActionText  = view.findViewById(R.id.delete_plans_action_text);;


        addPlanner.show();
        deleteAll.show();
        addAlarmActionText.setVisibility(View.VISIBLE);
        addPersonActionText.setVisibility(View.VISIBLE);




        return view;
    }

    void set_on_click(Button button,String day2){

        button.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                plannerListViewModel.getPlannersDay(day2).observe(getViewLifecycleOwner(), new Observer<List<Planner>>() {

                    @Override
                    public void onChanged(List<Planner> Planners) {
                        if (Planners != null) {
                            plannerRecyclerViewAdapter.setPlans(Planners);
                        }
                    }
                });
                plannerRecyclerViewAdapter.notifyDataSetChanged();
            } else {

                plannerRecyclerViewAdapter.getPlans0();
                plannerRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onToggle(Planner plan) {
        if (plan.isStarted()) {
            plan.cancelAlarm(getContext());
            plannerListViewModel.update(plan);
        } else {
            plan.schedule(getContext());
            plannerListViewModel.update(plan);
        }
    }
}