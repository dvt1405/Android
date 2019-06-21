package com.fitness.timer;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fitness.MainActivity;
import com.fitness.R;

import java.util.Calendar;
import java.util.zip.Inflater;

public class Fragment_timer extends Fragment {
    Calendar calendar;
    Button btnSetTimer;
    TimePicker timerPicker;
    Button btnEndTimer;
    TextView textViewShowTimer;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    NotificationCompat.Builder nBuilder;
    NotificationManager notificationManager;
    final int notificationID = 1405;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timerpicker, container, false);
        init(view);
        final Intent intent = new Intent(getActivity(),AlarmReceiver.class);
        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timerPicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timerPicker.getCurrentMinute());

                int gio = timerPicker.getCurrentHour();
                int phut = timerPicker.getCurrentMinute();
                String strphut = "";
                if (phut < 10) {
                    strphut = "0" + String.valueOf(phut);
                } else strphut += phut;
                intent.putExtra("extra","on");
                pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                textViewShowTimer.setText(gio + " : " + strphut);
            }
        });
        btnEndTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("extra","off");
                getActivity().sendBroadcast(intent);
                alarmManager.cancel(pendingIntent);


            }
        });


        return view;
    }

    private void init(View view) {
        calendar = Calendar.getInstance();
        btnSetTimer = view.findViewById(R.id.buttonSetTimer);
        btnEndTimer = view.findViewById(R.id.buttonEndTimer);
        textViewShowTimer = view.findViewById(R.id.textViewTimer);
        timerPicker = view.findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
    }
}
