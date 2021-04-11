package com.proj.planed.ui.alarms;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.proj.planed.NavigationActivity;
import com.proj.planed.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;
    private OnToggleAlarmListener listener;

    public AlarmRecyclerViewAdapter(OnToggleAlarmListener listener) {
        this.alarms = new ArrayList<Alarm>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);

        return new AlarmViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        try {
            holder.bind(alarm);
            holder.itemView.findViewById(R.id.card_view_alarm).setOnClickListener( v -> {
                Log.d("TAG", "onClick " + position);

                Bundle bundle = new Bundle();

                bundle.putInt("alarmid", alarm.getAlarmId());
                bundle.putInt("hour", alarm.getHour());
                bundle.putInt("minuite", alarm.getMinute());
                bundle.putString("title", alarm.getTitle());
                bundle.putLong("created",alarm.getCreated());
                bundle.putBoolean("started",alarm.isStarted());
                bundle.putBoolean("recurring",alarm.isRecurring());
                bundle.putBoolean("mon",alarm.isMonday());
                bundle.putBoolean("tue",alarm.isTuesday());
                bundle.putBoolean("wed",alarm.isWednesday());
                bundle.putBoolean("thu",alarm.isThursday());
                bundle.putBoolean("fri",alarm.isFriday());
                bundle.putBoolean("sat",alarm.isSaturday());
                bundle.putBoolean("sun",alarm.isSunday());
                bundle.putString("instruction", alarm.getInstruction());
                bundle.putString("condition", alarm.getCondition());
                bundle.putString("med", alarm.getMed_type());
                bundle.putInt("freq", alarm.getFrequency());
                bundle.putSerializable("alarm",alarm);

                Navigation.findNavController(v).navigate(R.id.action_alarmsListFragment_to_editAlarmFragment,bundle);
                notifyDataSetChanged();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
    }

    public Alarm getWordAtPosition (int position) {
        return alarms.get(position);
    }

}

