package com.proj.planed.ui.alarms;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;
import com.proj.planed.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAlarmFragment extends Fragment {
    TextInputLayout time_selected;
    TimePicker timePicker;
    @BindView(R.id.pill_instruction)
    TextInputLayout pillInstruction;
    @BindView(R.id.pill_frequency)
    TextInputLayout pillFrequency;
    @BindView(R.id.pill_description)
    TextInputLayout pillDescription;
    @BindView(R.id.spinner_pill_type)
    Spinner spinner_pillType;
    String pillType;

    @BindView(R.id.fragment_createalarm_title)
    TextInputLayout title;
    @BindView(R.id.fragment_createalarm_scheduleAlarm)
    Button scheduleAlarm;
    @BindView(R.id.fragment_createalarm_recurring)
    CheckBox recurring;
    @BindView(R.id.fragment_createalarm_checkMon)
    CheckBox mon;
    @BindView(R.id.fragment_createalarm_checkTue)
    CheckBox tue;
    @BindView(R.id.fragment_createalarm_checkWed)
    CheckBox wed;
    @BindView(R.id.fragment_createalarm_checkThu)
    CheckBox thu;
    @BindView(R.id.fragment_createalarm_checkFri)
    CheckBox fri;
    @BindView(R.id.fragment_createalarm_checkSat)
    CheckBox sat;
    @BindView(R.id.fragment_createalarm_checkSun)
    CheckBox sun;
    @BindView(R.id.fragment_createalarm_recurring_options)
    LinearLayout recurringOptions;


    int alarmId, alarmId_orig, hour, minute;

    Boolean timebtn = false;

    private CreateAlarmViewModel createAlarmViewModel;
    String[] type_pill = { "Tablet", "Pills" };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlarmViewModel = new ViewModelProvider(this).get(CreateAlarmViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createalarm, container, false);
        ButterKnife.bind(this, view);



        alarmId_orig = getArguments().getInt("alarmid");
        hour = getArguments().getInt("hour");
        minute = getArguments().getInt("minuite");
        title.getEditText().setText(getArguments().getString("title"));
        pillDescription.getEditText().setText(getArguments().getString("condition"));
        pillFrequency.getEditText().setText(""+getArguments().getInt("freq"));

        pillType = getArguments().getString("med");

        pillInstruction.getEditText().setText(getArguments().getString("instruction"));
        recurring.setChecked(getArguments().getBoolean("recurring"));
        mon.setChecked(getArguments().getBoolean("mon"));
        tue.setChecked(getArguments().getBoolean("tue"));
        wed.setChecked(getArguments().getBoolean("wed"));
        thu.setChecked(getArguments().getBoolean("thu"));
        fri.setChecked(getArguments().getBoolean("fri"));
        sat.setChecked(getArguments().getBoolean("sat"));
        sun.setChecked(getArguments().getBoolean("sun"));

        final Calendar c = Calendar.getInstance();
        time_selected = view.findViewById(R.id.time_text);

        time_selected.getEditText().setText(hour + ":" + minute);

        time_selected.setEnabled(false);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        timePicker = view;
                        timePicker.setIs24HourView(true);
                        time_selected.getEditText().setText(hourOfDay + ":" + minute);
                    }
                }, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), false);


        Button selectTime = view.findViewById(R.id.select_time);
        selectTime.setOnClickListener(v -> {
            timebtn = true;
            timePickerDialog.show();
        });




        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    recurringOptions.setVisibility(View.VISIBLE);
                } else {
                    recurringOptions.setVisibility(View.GONE);
                }
            }
        });


        ArrayAdapter ad = new ArrayAdapter(  getContext(),  android.R.layout.simple_spinner_item,   type_pill);

        ad.setDropDownViewResource(android.R.layout .simple_spinner_dropdown_item);

        spinner_pillType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pillType = type_pill[position];
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinner_pillType.setPrompt("Select Type");
        spinner_pillType.setAdapter(ad);


        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();

                Navigation.findNavController(v).navigate(R.id.action_editAlarmFragment_to_alarmsListFragment);

            }
        });


        return view;
    }



    private void scheduleAlarm() {
        alarmId = new Random().nextInt(Integer.MAX_VALUE);

        if(timebtn) {
            hour = TimePickerUtil.getTimePickerHour(timePicker);
            minute = TimePickerUtil.getTimePickerMinute(timePicker);
        }

        Alarm alarm = new Alarm(
                alarmId,
                hour,
                minute,
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
                pillInstruction.getEditText().getText().toString(),
                pillDescription.getEditText().getText().toString(),
                pillType,
                Integer.parseInt(pillFrequency.getEditText().getText().toString())
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
        Alarm alarm1 = (Alarm) getArguments().getSerializable("alarm");
        createAlarmViewModel.delete(alarm1);


        new AlertDialog.Builder(getContext())
                .setTitle("Alarm Edited")
                .setMessage("Your Alarm has been Edited!")

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
