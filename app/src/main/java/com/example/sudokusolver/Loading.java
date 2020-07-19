package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;

public class Loading extends AppCompatActivity {
    private static String ele="";
    Timer timer;
    int p=0;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        p=1;
        startActivity(new Intent(Loading.this, SolveActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        ele = getIntent().getStringExtra("boardEle");


        timer = new Timer();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            if(p==0){
                Intent intent = new Intent(Loading.this, DisplayActivity.class);
                intent.putExtra("boardEle",ele);
                startActivity(intent);
                finish();
            }
            }
        }, 2000);




    }
}
