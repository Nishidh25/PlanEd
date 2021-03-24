package com.proj.planed.ui.planner;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.proj.planed.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PlannerViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTime;
    private ImageView alarmRecurring;
    private TextView alarmRecurringDays;
    private TextView alarmTitle;

    SwitchMaterial alarmStarted;

    private OnTogglePlannerListener listener;

    public PlannerViewHolder(@NonNull View itemView, OnTogglePlannerListener listener) {
        super(itemView);

        alarmTime = itemView.findViewById(R.id.item_alarm_time);
        alarmStarted = itemView.findViewById(R.id.item_alarm_started);
        alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
        alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
        alarmTitle = itemView.findViewById(R.id.item_alarm_title);

        this.listener = listener;
    }

    public void bind(Planner planner) throws Exception {
        String alarmText = String.format("%02d:%02d", planner.getHour(), planner.getMinute());
        String result = LocalTime.parse(alarmText, DateTimeFormatter.ofPattern("HH:mm")).format(DateTimeFormatter.ofPattern("hh:mm a"));
        alarmTime.setText(result);
        //alarmTime.setText(conv24to12h(alarmText));
        alarmStarted.setChecked(planner.isStarted());

        if (planner.isRecurring()) {
            alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
            alarmRecurringDays.setText(planner.getRecurringDaysText());
        } else {
            alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
            alarmRecurringDays.setText("Once Off");
        }

        if (planner.getTitle().length() != 0) {
            alarmTitle.setText(String.format("%s | %s | %d", planner.getTitle()));
        } else {
            alarmTitle.setText(String.format("%s | %d | %d", "Activity", planner.getPlanId(), planner.getCreated()));
        }

        alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onToggle(planner);
            }
        });
    }



}
