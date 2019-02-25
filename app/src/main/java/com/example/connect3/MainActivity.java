package com.example.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: empty, 1: yellow, 2: red

    int activePlayer = 1;
    int[] board = {0,0,0,0,0,0,0,0,0};
    int tableCount = 0;
    int[][] winCheck = {{0,1,2}, {3,4,5}, {6,7,8}, {0, 3, 6}, {1,4,7}, {2,5,8}, {0, 4, 8}, {2, 4, 6}};

    public int checkWin(int player){
        int win = 0;
        for(int i = 0; i < winCheck.length; i++){
            int[] con = winCheck[i];
            if(board[con[0]] == player && board[con[1]] == player &&board[con[2]] == player){
                win = 1;
                break;
            }
        }

        if(win == 0 && tableCount == 9){
            win = 2;
        }

        return win;
    }


    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int tag = Integer.parseInt(counter.getTag().toString());
        if(board[tag] == 0){
            counter.setTranslationY(-1500);
            board[tag] = activePlayer;
            tableCount++;
            if(activePlayer == 1){
                counter.setImageResource(R.drawable.yellow);
            } else {
                counter.setImageResource(R.drawable.red);
            }
            counter.animate().translationYBy(1500).setDuration(300);

            int isWin = checkWin(activePlayer);
            if( isWin == 0){
                activePlayer = activePlayer == 1 ? 2 : 1;
            } else {
                RelativeLayout result = (RelativeLayout) findViewById(R.id.resultContainer);
                TextView text = (TextView) findViewById(R.id.winText);
                String winner = activePlayer == 1 ? "Yellow" : "Red";
                result.setVisibility(View.VISIBLE);
                if(isWin == 1) {
                    //Toast.makeText(this, "Player wins: " + activePlayer, Toast.LENGTH_SHORT).show();
                    text.setText(winner + " player wins!!");
                } else {
                    text.setText("Draw !!");
                }
            }

        }
    }

    public void restartGame(View view){
        Log.i("info", "RESTART GAME BITCH");
        activePlayer = 1;
        tableCount = 0;
        for(int i = 0; i < board.length; i++){
            board[i] = 0;
        }
        RelativeLayout result = (RelativeLayout) findViewById(R.id.resultContainer);
        result.setVisibility(View.INVISIBLE);
        android.support.v7.widget.GridLayout grid = (android.support.v7.widget.GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < grid.getChildCount(); i++){
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
