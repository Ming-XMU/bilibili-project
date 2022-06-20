package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputet;
    private Button getTime,startTime,stopTime,clearTime;
    private TextView time;
    private int i = 0;
    private Timer timer = null;
    private TimerTask task = null;
    private Message  message ;
    private boolean isClear = false;
    private TimeTextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        inputet = (EditText) findViewById(R.id.input_time);
        getTime = (Button) findViewById(R.id.get_time);
        startTime = (Button) findViewById(R.id.start_time);
        stopTime = (Button) findViewById(R.id.stop_time);
        clearTime = (Button) findViewById(R.id.clear_time);
        time = (TextView) findViewById(R.id.time);
        timeTextView = (TimeTextView)findViewById(R.id.time_text_view);
        getTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        stopTime.setOnClickListener(this);
        clearTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_time:
                time.setText(inputet.getText().toString());
                i = Integer.parseInt(inputet.getText().toString());
                timeTextView.setCountDownTime(i);
                break;

            case R.id.start_time:
                startTime();
                timeTextView.startTime();
                break;
            case R.id.stop_time:
                stopTime();
                timeTextView.stopTime();
                break;
            case R.id.clear_time:
                clearTime();
                timeTextView.clearTime();
                break;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 >= 0){
                time.setText(msg.arg1+"");
            }else{
                time.setText("0");
            }
            startTime();
        };
    };

    public void startTime(){
        isClear = false;
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                i--;
                message = mHandler.obtainMessage();
                message.arg1 = i;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000);
    }

    public void stopTime(){
        timer.cancel();
    }
    public void clearTime(){
        stopTime();
        i = 0;
        message.arg1 = 0;
        time.setText("0");
    }

}