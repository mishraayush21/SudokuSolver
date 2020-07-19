package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SolveActivity extends AppCompatActivity {


    private static Button[][] buttons = new Button[9][9];
    private static Button[] numkeys = new Button[9];
    private Button delBtn,clrBtn,solveBtn;

    int curr_i, curr_j, curr_k;


    private static int board[][] = new  int[9][9];
    private static String ele = "";
    private static boolean valid = true;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String buttonID = "button" + String.valueOf(i) + String.valueOf(j);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

            }
        }

//        for (int k = 1; k < 10; k++)
//        {
//            String buttonID = "button" + String.valueOf(k);
//            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
//            numkeys[k] = findViewById(resID);
//        }

        numkeys[0] = findViewById(R.id.button1);
        numkeys[1] = findViewById(R.id.button2);
        numkeys[2] = findViewById(R.id.button3);
        numkeys[3] = findViewById(R.id.button4);
        numkeys[4] = findViewById(R.id.button5);
        numkeys[5] = findViewById(R.id.button6);
        numkeys[6] = findViewById(R.id.button7);
        numkeys[7] = findViewById(R.id.button8);
        numkeys[8] = findViewById(R.id.button9);








        delBtn = findViewById(R.id.buttonDEL);
        clrBtn = findViewById(R.id.buttonCLR);
        solveBtn = findViewById(R.id.buttonSolve);




        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                final int ii=i,jj=j;

                curr_i = i;
                curr_j = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                       resetOtherSelection();

                       if(buttons[ii][jj].getText().toString().equals(""))
                        buttons[ii][jj].setText("⬜");
                       else if(Character.isDigit(buttons[ii][jj].getText().toString().charAt(0))) {
                           buttons[ii][jj].setTextColor(Color.parseColor("#29a10b"));
                           buttons[ii][jj].setTextScaleX((float) 1.5);
                       }
                        //insertKeys(ii,jj);
                        for (int k = 0; k< 9; k++)
                        {
                            curr_k = k;
                            final int kk = k;
                            numkeys[k].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String num = numkeys[kk].getText().toString();
                                    buttons[ii][jj].setTextColor(Color.parseColor("#00008B"));
                                    buttons[ii][jj].setTextScaleX((float)1);
                                    buttons[ii][jj].setText(num);
                                }
                            });
                        }




                        delBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                buttons[ii][jj].setText("");
                            }
                        });

                        clrBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                clearBoard();
                            }
                        });

                        solveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                               // Toast.makeText(SolveActivity.this, "ssssssssssssss", Toast.LENGTH_SHORT).show();
                                solveThePuzzle();

                                if(valid)
                                {
                                for (int i = 0; i < 9; i++) {
                                    for (int j = 0; j < 9; j++) {

                                        ele += Integer.toString(board[i][j]);

                                    }
                                }


                                Intent intent = new Intent(SolveActivity.this, Loading.class);
                                intent.putExtra("boardEle",ele);
                                startActivity(intent);
                                }
                                ele="";
                                valid = false;







                            }
                        });



                    }
                });


            }




        }




        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearBoard();
            }
        });

        solveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                solveThePuzzle();


                if(valid)
                {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {

                            ele += Integer.toString(board[i][j]);

                        }
                    }


                    Intent intent = new Intent(SolveActivity.this, Loading.class);
                    intent.putExtra("boardEle",ele);
                    startActivity(intent);
                }
                ele="";
                valid = false;





            }
        });






    }

    private void resetOtherSelection() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if(buttons[i][j].getText().toString().equals("⬜"))
                buttons[i][j].setText("");

            }
        }

    }






    private void solveThePuzzle() {
        readBoard();

        if(!isValid())
        {
            Toast.makeText(this, "Sudoku is invalid!!", Toast.LENGTH_SHORT).show();


        }

         else if(solveIt()){
         startActivity(new Intent(SolveActivity.this, DisplayActivity.class));

        }


    }

    private static boolean solveIt() {


        int i,j,k=0;
        int row=-1,col=-1;
        int picked  ;
        boolean isEmpty = false;

        for(i = 0; i < 9; i++)
        {
            for(j = 0; j<9; j++)
            {
                if(board[i][j] == 0 )
                {
                    row = i;
                    col = j;
                    isEmpty = true;
                    break;

                }
            }

            if(isEmpty)
                break;


        }

        if(!isEmpty)
            return true;
        else
        {
            for(k = 1; k<=9; k++)
            {


                picked = k;

                boolean row_cond = check_row(row, col, picked) , col_cond = check_col(row, col, picked), box_cond = check_box(row, col, picked);

                if(row_cond && col_cond && box_cond)
                {
                    board[row][col] = picked;
                    if(solveIt())
                        return true;
                    else
                        board[row][col] = 0;

                }

            }


        }



        return false;



    }

    private void readBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {


                if(!buttons[i][j].getText().toString().equals(""))
                board[i][j] =  Integer.valueOf(buttons[i][j].getText().toString());
                else
                    board[i][j] = 0;

            }
        }

    }

    private static boolean check_col(int rowno, int colno, int x) {
        for(int i =0; (i<9 ); i++)
        {

            if(board[i][colno] == x && i != rowno)
                return(false);

        }

        return(true);
    }


    private static boolean check_row(int rowno, int colno,  int x) {
        for(int i =0; (i<9 ); i++)
            if(board[rowno][i] == x && i != colno)
                return(false);

        return(true);
    }

    private static boolean check_box(int rowno, int colno, int x) {
        int i,j;

        for( i = (rowno / 3)*3; i< (rowno / 3)*3 + 3; i++)
        {
            for( j = (colno / 3)*3; j< (colno / 3)*3 + 3; j++)
            {

                if(board[i][j] == x && i != rowno && j != colno)
                    return(false);

            }
        }

        return(true);
    }





    private static boolean isValid()
    {
        for(int i = 0; i<9; i++ )
            for(int j=0; j<9; j++ )
                if(board[i][j] != 0)
                {
                    valid = false;

                    if(check_col(i, j, board[i][j]) == false || check_row(i,j, board[i][j] ) == false  || check_box(i, j, board[i][j]) == false)
                        return false;
                }

        valid = true;
        return true;
    }


    private void clearBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                buttons[i][j].setText("");

            }
        }

    }







}
