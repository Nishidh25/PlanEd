package com.proj.planed.ui.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.proj.planed.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    TextView textview1;
    TimePicker timepicker;
    Button changetime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createNotificationChannel();


        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));



        textview1 = root.findViewById(R.id.textView_time);
        timepicker= root.findViewById(R.id.timePicker);
        //timepicker.setIs24HourView(true);
        changetime= root.findViewById(R.id.button1);

        textview1.setText(getCurrentTime());

        changetime.setOnClickListener(view -> {
            textview1.setText(getCurrentTime());
            String time = getCurrentTime();


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timepicker.getHour());
            calendar.set(Calendar.MINUTE, timepicker.getMinute());


            int min = timepicker.getHour()* 60 + timepicker.getMinute();
            long usertime = TimeUnit.MINUTES.toMillis(min);

            //calendar.set(Calendar.HOUR_OF_DAY, TimePicker.getCurrentHour());
            //calendar.set(Calendar.MINUTE, TimePicker.getCurrentMinute());

            Intent intent = new Intent(getContext(), AlertBroadcast.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 0,intent,0);

            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Log.d("TAG", "onCreateView: 1" +SystemClock.elapsedRealtime() + " " + usertime);

            alarmManager.set(AlarmManager.RTC,calendar.getTimeInMillis(), alarmIntent);
            Log.d("TAG", "onCreateView: 2");
        });
        return root;
    }

    public String getCurrentTime(){
        String currentTime="Current Time: "+timepicker.getHour()+":"+timepicker.getMinute();
        return currentTime;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PlanEd Alert Channel" ;              //getString(R.string.channel_name);
            String description = "Channel for PlanEd Alert";          //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("PlanEdChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
