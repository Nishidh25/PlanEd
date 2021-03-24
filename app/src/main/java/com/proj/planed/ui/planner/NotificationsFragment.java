package com.proj.planed.ui.planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.proj.planed.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    TextView textview1;
    CalendarView calendarView;
    Button btn1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));



        textview1 = root.findViewById(R.id.textView_time);
        calendarView = root.findViewById(R.id.calendarView);
        btn1 = root.findViewById(R.id.button1);


        return root;
    }

}
