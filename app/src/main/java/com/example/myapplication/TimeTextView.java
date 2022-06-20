package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTextView extends androidx.appcompat.widget.AppCompatTextView {

    public TimeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //记录是否清空
    private boolean isClear = false;
    //记录是否完成计时
    private boolean isFinish = false;
    //倒计时时间
    private int coutDownTime = 0;


    private Message  message ;
    private Timer timer = null;
    private TimerTask task = null;



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 >= 0){
                setText(msg.arg1+"");
            }else{
                isFinish = true;
                setText("0");
            }
            startTime();
        };
    };


    public void startTime(){
        if(isClear){
            setText("0");
        }else{
//            isClear = false;
            timer = new Timer();
            task = new TimerTask() {
                 @Override
                public void run() {
                    coutDownTime--;
                    message = mHandler.obtainMessage();
                    message.arg1 = coutDownTime;
                    mHandler.sendMessage(message);
                }
            };
            timer.schedule(task, 1000);
        }

    }

    public void stopTime(){
        timer.cancel();
    }
    public void clearTime(){
        stopTime();
        coutDownTime = 0;
        message.arg1 = 0;
        setText("0");
    }

    public void setCountDownTime(int i){
        this.coutDownTime = i;
    }
    public boolean getIsClear(){
        return isClear;
    }
    public boolean getIsFinish(){
        return isFinish;
    }

}
