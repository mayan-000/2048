package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Game4x4 extends AppCompatActivity {

    private TextView[][] blocks = new TextView[5][5];
    private Model4x4Class _4x4;
    private ConstraintLayout layout, layout2;
    private ImageButton resetButton;
    private TextView Score, HighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4x4);

        getID();

        layout = findViewById(R.id.Layout4x4);
        resetButton = findViewById(R.id.restart4x4);
        Score = findViewById(R.id.score4x4);
        HighScore = findViewById(R.id.highScore4x4);
        layout2 = findViewById(R.id.constraintLayout2);

        _4x4 = new Model4x4Class(this, blocks, Score, HighScore, layout, resetButton,
                layout2);


        layout.setOnTouchListener(new OnSwipeTouchListener(Game4x4.this){
            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                _4x4.up();
            }

            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
                _4x4.down();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                _4x4.left();
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                _4x4.right();
            }
        });

        resetButton.setOnClickListener(v -> {
            _4x4.reset();
        });

    }


    private void getID(){
        blocks[1][1] = findViewById(R.id.block11_4x4);
        blocks[1][2] = findViewById(R.id.block12_4x4);
        blocks[1][3] = findViewById(R.id.block13_4x4);
        blocks[1][4] = findViewById(R.id.block14_4x4);
        blocks[2][1] = findViewById(R.id.block21_4x4);
        blocks[2][2] = findViewById(R.id.block22_4x4);
        blocks[2][3] = findViewById(R.id.block23_4x4);
        blocks[2][4] = findViewById(R.id.block24_4x4);
        blocks[3][1] = findViewById(R.id.block31_4x4);
        blocks[3][2] = findViewById(R.id.block32_4x4);
        blocks[3][3] = findViewById(R.id.block33_4x4);
        blocks[3][4] = findViewById(R.id.block34_4x4);
        blocks[4][1] = findViewById(R.id.block41_4x4);
        blocks[4][2] = findViewById(R.id.block42_4x4);
        blocks[4][3] = findViewById(R.id.block43_4x4);
        blocks[4][4] = findViewById(R.id.block44_4x4);
    }


}