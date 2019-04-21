package com.fitness;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import model.Practice;
import model.TestPlayMusicThread;

public class FragmentGuidePractice extends Fragment {
    private TextView textView;
    private CollapsingToolbarLayout toolbarLayout;
    private ImageButton backButton;

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Button buttonStart;
    private Button buttonPause;
    private Handler handler = new Handler();
    private TextView textViewTimer;
    private boolean isPlaying;
    TestPlayMusicThread testPlayMusicThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide_practice, container, false);
        initView(view);
        Bundle getPractice = getArguments();
        Practice practice = (Practice) getPractice.getSerializable("practice");
        textView.setText(practice.getName());

        backButton.setOnClickListener(onButtonBackCliked);
        //-----------Play Music---------------
        int idsong = R.raw.dancetheduc;
        mediaPlayer = MediaPlayer.create(container.getContext(), idsong);
        buttonStart.setOnClickListener(onButtonStartClicked);
        buttonPause.setOnClickListener(onButtonPauseClicked);
        seekBar.setOnSeekBarChangeListener(onSeekBarChanged);
        if(savedInstanceState!=null){
            String time = savedInstanceState.getString("time");
            seekBar.setProgress(Integer.parseInt(time));
            mediaPlayer.seekTo(Integer.parseInt(time));
            Log.e("Restore on create new", "Msg");
        }


        return view;
    }

    public void initView(View view) {
        toolbarLayout = view.findViewById(R.id.toolbarGuidePractice);
        textView = toolbarLayout.findViewById(R.id.textToolbar);
        backButton = toolbarLayout.findViewById(R.id.buttonToolbar);
        seekBar = view.findViewById(R.id.seekBar);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonPause = view.findViewById(R.id.buttonStop);
        textViewTimer = view.findViewById(R.id.textTime);
        isPlaying = false;
    }

    public String millisecondToString(int milli) {
        long minute = TimeUnit.MILLISECONDS.toMinutes(milli);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);
        seconds = seconds - minute * 60;
        return minute + ":" + seconds;
    }

    private View.OnClickListener onButtonBackCliked
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handler.removeCallbacks(testPlayMusicThread);
            mediaPlayer.stop();
            getFragmentManager().popBackStack("backStackListPractice", 1);
        }
    };
    //-----Start play practice
    private View.OnClickListener onButtonStartClicked
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!isPlaying) {
                int time = mediaPlayer.getDuration();
                Log.e("Time: ", time + "");
                int current = mediaPlayer.getCurrentPosition();
                if (current == 0) {
                    seekBar.setMax(time);
                    textViewTimer.setText(millisecondToString(time));
                } else if (current == time) {
                    mediaPlayer.reset();
                }
                mediaPlayer.start();
                buttonStart.setBackgroundResource(R.drawable.ic_pause);
                isPlaying = true;
                testPlayMusicThread = new TestPlayMusicThread(mediaPlayer, seekBar, handler, textViewTimer);
                handler.postDelayed(testPlayMusicThread,50);
            }else {
                mediaPlayer.pause();
                buttonStart.setBackgroundResource(R.drawable.ic_play);
                isPlaying = false;
            }
        }
    };
    private View.OnClickListener onButtonPauseClicked
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.pause();
        }
    };
    private SeekBar.OnSeekBarChangeListener onSeekBarChanged
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            testPlayMusicThread.getSeekBar().setProgress(seekBar.getProgress());
            testPlayMusicThread.getMediaPlayer().seekTo(seekBar.getProgress());
            testPlayMusicThread.getTextView().setText(seekBar.getProgress()+"");
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("time", testPlayMusicThread.getMediaPlayer().getCurrentPosition()+"");
        Log.e("Saved state:", "OK");
        this.setArguments(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
        String time = savedInstanceState.getString("time");
        seekBar.setProgress(Integer.parseInt(time));
        mediaPlayer.seekTo(Integer.parseInt(time));
        }
        Log.e("Restore", "Msg");
    }
}
