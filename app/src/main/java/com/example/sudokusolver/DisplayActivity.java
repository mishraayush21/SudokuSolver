package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    private static Button[][] buttons = new Button[9][9];
    Button exitBtn,anotherBtn;

    private static int boards[][] = new  int[9][9];
    private static String ele="";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent( DisplayActivity.this, MainActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
       ele = getIntent().getStringExtra("boardEle");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String buttonID = "button" + String.valueOf(i) + String.valueOf(j);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

            }
        }



        exitBtn = findViewById(R.id.buttonExit);
        anotherBtn = findViewById(R.id.buttonSolveAnother);


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                int p = i*9 + j;
                buttons[i][j].setTextColor(Color.parseColor("#00008B"));
                buttons[i][j].setText(ele.substring(p,p+1));
            }
        }








        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            System.exit(0);

            }
        });

        anotherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DisplayActivity.this, SolveActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }



        });




    }
}
