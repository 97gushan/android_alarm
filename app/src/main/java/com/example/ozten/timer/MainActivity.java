package com.example.ozten.timer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public EditText edit_hour;
    public EditText edit_minute;
    public EditText edit_second;

    public Button button_run;
    public Button button_pause;

    public Handler h;
    public Timer t;

    private String hour = "";
    private String minute = "";
    private String second = "";
    private String milli_second = "";
    public long start_time = 0;

    public long time = 0;
    private long timer_start_value = 15000;
    public boolean timer_run = true;
    public boolean first_run = true;
    public int sound_played = 0;

    public MediaPlayer mp;
    public AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_hour = (EditText) findViewById(R.id.edit_hour);
        edit_minute = (EditText) findViewById(R.id.edit_minute);
        edit_second = (EditText) findViewById(R.id.edit_second);

        button_run = (Button) findViewById(R.id.button_run);
        button_pause = (Button) findViewById(R.id.button_pause);

        h = new Handler();
        t = new Timer();


        mp = MediaPlayer.create(this, R.raw.alarm);

    }

    Runnable r = new Runnable(){

        public void run(){

            time = System.currentTimeMillis() - start_time;

            set_clock_values(timer_start_value - time);

            edit_hour.setText(hour);
            edit_minute.setText(minute);
            edit_second.setText(second);

            if(timer_start_value-time > 0){
                timer_control();
            }else{
                mp.start();

                timer_run = false;

                button_run.setEnabled(true);
                button_pause.setEnabled(false);

                edit_hour.setEnabled(true);
                edit_minute.setEnabled(true);
                edit_second.setEnabled(true);
            }
        }
    };

    public void set_clock_values(long time){
        long temp = 0;
        hour = Long.toString(time / 3600000);
        temp = time % 3600000;
        minute = Long.toString(temp / 60000);
        temp = temp % 60000;
        second = Long.toString(temp / 1000);
        temp = temp % 1000;
        milli_second = Long.toString(temp);
    }

    public long get_timer_value(){
        try{
            Long tmp_hour = (long)Integer.parseInt(edit_hour.getText().toString());
            Long tmp_minute = (long)Integer.parseInt(edit_minute.getText().toString());
            Long tmp_second = (long)Integer.parseInt(edit_second.getText().toString())+1;

            return tmp_hour * 3600000 + tmp_minute * 60000 + tmp_second * 1000;
        }catch(Exception e){
            return 60000;
        }

    }

    public void timer_control(){
        if(timer_run){
            h.postDelayed(r, 50);
        }
    }

    public void startTimer(View view){
        start_time = System.currentTimeMillis();
        timer_start_value = get_timer_value();

        timer_run = true;

        button_run.setEnabled(false);
        button_pause.setEnabled(true);

        edit_hour.setEnabled(false);
        edit_minute.setEnabled(false);
        edit_second.setEnabled(false);

        if(mp.isPlaying()){
            mp.pause();
        }

        timer_control();
    }

    public void pause_timer(View view){
        timer_run = false;

        button_run.setEnabled(true);
        button_pause.setEnabled(false);

        edit_hour.setEnabled(true);
        edit_minute.setEnabled(true);
        edit_second.setEnabled(true);
    }
}


