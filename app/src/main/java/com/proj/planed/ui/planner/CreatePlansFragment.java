package com.proj.planed.ui.planner;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.proj.planed.R;
import com.proj.planed.ui.alarms.TimePickerUtil;

import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePlansFragment extends Fragment {
    TextInputLayout time_selected;
    TimePicker timePicker;
   
    @BindView(R.id.planner_description)
    TextInputLayout planner_desc;

    @BindView(R.id.fragment_createplan_title)
    TextInputLayout title;
    @BindView(R.id.fragment_createplan_scheduleActivity)
    Button scheduleAlarm;
    @BindView(R.id.fragment_createplan_recurring)
    CheckBox recurring;
    @BindView(R.id.fragment_createplan_checkMon)
    CheckBox mon;
    @BindView(R.id.fragment_createplan_checkTue)
    CheckBox tue;
    @BindView(R.id.fragment_createplan_checkWed)
    CheckBox wed;
    @BindView(R.id.fragment_createplan_checkThu)
    CheckBox thu;
    @BindView(R.id.fragment_createplan_checkFri)
    CheckBox fri;
    @BindView(R.id.fragment_createplan_checkSat)
    CheckBox sat;
    @BindView(R.id.fragment_createplan_checkSun)
    CheckBox sun;
    @BindView(R.id.fragment_createplan_recurring_options)
    LinearLayout recurringOptions;

    private CreatePlannerViewModel createPlannerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createPlannerViewModel = new ViewModelProvider(this).get(CreatePlannerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createplan, container, false);


        final Calendar c = Calendar.getInstance();
        time_selected = view.findViewById(R.id.time_text);
        time_selected.setEnabled(false);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view1, hourOfDay, minute) -> {
                    timePicker = view1;
                    timePicker.setIs24HourView(true);
                    time_selected.getEditText().setText(hourOfDay + ":" + minute);
                }, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), false);


        Button selectTime = view.findViewById(R.id.select_time);
        selectTime.setOnClickListener(v -> {
            timePickerDialog.show();
        });


        ButterKnife.bind(this, view);

        recurring.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                recurringOptions.setVisibility(View.VISIBLE);
            } else {
                recurringOptions.setVisibility(View.GONE);
            }
        });


        scheduleAlarm.setOnClickListener(v -> {
            scheduleAlarm();
            Navigation.findNavController(v).navigate(R.id.action_createPlannerFragment_to_plannerListFragment);

        });


        return view;
    }



    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        String day = "monday";
        if (tue.isChecked()){
            day = "tuesday";
        }if (wed.isChecked()){
            day = "wednesday";
        }if (thu.isChecked()){
            day = "thursday";
        }if (fri.isChecked()){
            day = "friday";
        }if (sat.isChecked()){
            day = "saturday";
        }if (sun.isChecked()){
            day = "sunday";
        }


        Planner planner = new Planner(
                alarmId,
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),

                title.getEditText().getText().toString(),
                System.currentTimeMillis(),
                true,
                recurring.isChecked(),
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked(),
                planner_desc.getEditText().getText().toString(),
                day
        );

        createPlannerViewModel.insert(planner);

        planner.schedule(getContext());


        new AlertDialog.Builder(getContext())
                .setTitle("Alarm Created")
                .setMessage("Your Alarm has been created!")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(R.drawable.ic_alarm_static)
                .show();

    }
}
