package com.fitness;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import model.TestPlayMusicThread;

public class Fragment_CustomWorkScreen extends Fragment {
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button buttonStart;
    private Button buttonPause;
    private Handler handler = new Handler();
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customwork,container,false);
        initView(view);
        int idsong = R.raw.dancetheduc;
        mediaPlayer = MediaPlayer.create(container.getContext(), idsong);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time  =mediaPlayer.getDuration();
                Log.e("Time: ",time+"");
                int current = mediaPlayer.getCurrentPosition();
                if(current==0) {
                    seekBar.setMax(time);
                    textView.setText(millisecondToString(time));
                }else if(current == time) {
                    mediaPlayer.reset();
                }
                mediaPlayer.start();
                TestPlayMusicThread testPlayMusicThread = new TestPlayMusicThread(mediaPlayer,seekBar,handler, textView);

                testPlayMusicThread.run();
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });
        return view;
    }
    public void initView(View view) {
        seekBar = view.findViewById(R.id.seekBar);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonPause = view.findViewById(R.id.buttonStop);
        textView = view.findViewById(R.id.textTime);
    }
    public String millisecondToString(int milli) {
        long minute = TimeUnit.MILLISECONDS.toMinutes(milli);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);
        seconds = seconds - minute*60;
        return minute+":"+seconds;
    }

}
