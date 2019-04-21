package model;

import android.media.MediaPlayer;
import android.widget.SeekBar;
import android.widget.TextView;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class TestPlayMusicThread implements Runnable {
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;
    private TextView textView;

    public TestPlayMusicThread(MediaPlayer mediaPlayer, SeekBar seekBar) {
        this.mediaPlayer = mediaPlayer;
        this.seekBar = seekBar;
        this.handler = handler;
    }

    public TestPlayMusicThread(MediaPlayer mediaPlayer, SeekBar seekBar,Handler handler, TextView textView) {
        this.mediaPlayer = mediaPlayer;
        this.seekBar = seekBar;
        this.handler = handler;
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        int current = this.mediaPlayer.getCurrentPosition();
        int duration = this.mediaPlayer.getDuration();
        this.seekBar.setProgress(current);
        this.textView.setText(millisecondToString(duration-current));
        this.handler.postDelayed(this,50);
    }
    public String millisecondToString(int milli) {
        long minute = TimeUnit.MILLISECONDS.toMinutes(milli);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milli);
        seconds = seconds - minute*60;
        return minute+":"+seconds;
    }
}
