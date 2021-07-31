package com.example.a2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


public class Model4x4Class {
    private TextView[][] blocks;
    private Context context;
    private TextView Score, HighScore;
    private SharedPreferences sharedPreferences;
    private ViewGroup viewGroup, viewGroup2;
    private ImageButton resetButton;
    private int flag = 0;

    public Model4x4Class(Context context, TextView[][] blocks, TextView score, TextView highScore,
                         ConstraintLayout layout, ImageButton reset, ConstraintLayout layout2) {
        this.blocks = blocks;
        this.context = context;
        Score = score;
        HighScore = highScore;
        this.viewGroup = layout;
        this.resetButton = reset;
        this.viewGroup2 = layout2;

        sharedPreferences = context.getSharedPreferences("saveData",
                Context.MODE_PRIVATE);

        HighScore.setText(""+sharedPreferences.getInt("HighScore4x4",0));

        _2Random();
    }

    public boolean up(){
        int[][] array = new int[6][6];


        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                array[i][j] = Integer.parseInt("0"+blocks[j][i].getText().toString());
                blocks[j][i].setText("");
                AnimateUp(blocks[j][i]);
            }
        }


        int [][] ans = new int[5][5];
        calculate(array, ans);

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                blocks[j][i].setText("" + (ans[i][j]));
            }
        }

        removeZeroes();

        return _2Random();
    }

    public boolean down(){
        int[][] array = new int[6][6];


        for (int i = 1; i < 5; i++) {
            for (int j = 1, k = 4; j < 5; j++, k--) {
                array[i][j] = Integer.parseInt("0"+blocks[k][i].getText().toString());
                AnimateDown(blocks[k][i]);
            }
        }



        int [][] ans = new int[5][5];
        calculate(array, ans);

        for (int i = 1; i < 5; i++) {
            for (int j = 1, k = 4; j < 5; j++, k--) {
                blocks[k][i].setText("" + (ans[i][j]));
            }
        }

        removeZeroes();

        return _2Random();
    }

    public boolean left(){
        int[][] array = new int[6][6];

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                array[i][j] = Integer.parseInt("0"+blocks[i][j].getText().toString());
                AnimateLeft(blocks[i][j]);
            }
        }


        int [][] ans = new int[5][5];
        calculate(array, ans);

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                blocks[i][j].setText("" + (ans[i][j]));
            }
        }

        removeZeroes();

        return _2Random();
    }

    public boolean right(){
        int[][] array = new int[6][6];

        for (int i = 1; i < 5; i++) {
            for (int j = 1, k = 4; j < 5; j++, k--) {
                array[i][j] = Integer.parseInt("0"+blocks[i][k].getText().toString());
                AnimateRight(blocks[i][k]);
            }
        }


        int [][] ans = new int[5][5];
        calculate(array, ans);


        for (int i = 1; i < 5; i++) {
            for (int j = 1, k = 4; j < 5; j++, k--) {
                blocks[i][k].setText("" + (ans[i][j]));
            }
        }


        removeZeroes();


        return _2Random();

    }

    public void reset(){
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                blocks[i][j].setText("");
            }
        }

        Score.setText("0");

        _2Random();
    }

    private void calculate(int [][] array, int [][] ans){
        int _2048 = 0;
        int highScore = Integer.parseInt(HighScore.getText().toString());


        int num = 0;

        int [][] a = new int[5][6];

        for (int row = 1; row < 5; row++) {
            for (int col = 1, z = 1; col < 5; col++) {
                if(array[row][col]!=0){
                    a[row][z] = array[row][col];
                    z++;
                }
            }
        }


        for (int row = 1; row < 5; row++) {
            for (int col = 2, z = 1; col <= 5; col++, z++) {
                if(a[row][col]==a[row][col-1]){
                    ans[row][z] = 2*a[row][col];
                    num+=ans[row][z];
                    if(ans[row][z]==2048 && flag==0){
                        _2048++;
                        flag=1;
                    }
                    col++;
                }
                else{
                    ans[row][z] = a[row][col-1];
                }
            }
        }

        int score = Integer.parseInt(Score.getText().toString());
        score+=num;

        Score.setText(""+score);

        if(score>highScore){
            HighScore.setText(""+score);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("HighScore4x4",score);
            edit.apply();
        }


        if(_2048!=0){
            resetButton.setVisibility(View.INVISIBLE);
            viewGroup.setVisibility(View.INVISIBLE);
            viewGroup2.setVisibility(View.VISIBLE);

            TextView yes = viewGroup2.findViewById(R.id.YesButton4x4);
            TextView no = viewGroup2.findViewById(R.id.NoButton4x4);

            yes.setOnClickListener(v -> {
                resetButton.setVisibility(View.VISIBLE);
                viewGroup.setVisibility(View.VISIBLE);
                viewGroup2.setVisibility(View.INVISIBLE);
            });

            no.setOnClickListener(v -> {
                Toast.makeText(context, "GAME OVER", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            });
        }

    }

    private boolean _2Random(){

        ArrayList<Pair<Integer,Integer>> points = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if(blocks[i][j].getText().toString().equalsIgnoreCase("")){
                    points.add(new Pair<>(i,j));
                }
            }
        }

        if(points.size()<2){
            if(check());
            else{
                return false;
            }
        }
        else {
            int pos = new Random().nextInt(points.size());

            Pair<Integer, Integer> _1 = points.get(pos);

            points.remove(pos);

            pos = new Random().nextInt(points.size());

            Pair<Integer, Integer> _2 = points.get(pos);

            points.remove(pos);


            blocks[_1.first][_1.second].setText("2");
            blocks[_2.first][_2.second].setText("2");
        }


        setBG();

        return true;

    }

    private boolean check(){

        int[] x = new int[2];
        x[0] = -1;
        x[1] = 1;

        int[] y = new int[2];
        y[0] = -1;
        y[1] = 1;

        int cnt = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int _x = 0; _x < 2; _x++) {
                    for (int _y = 0; _y < 2; _y++) {
                        if(i+x[_x]<5 && i+x[_x]>0
                        && j+y[_y]<5 && j+y[_y]>0){
                            if(!blocks[i][j].getText().toString().equalsIgnoreCase("") &&
                                blocks[i][j].getText().toString()
                                .equalsIgnoreCase(blocks[i+x[_x]][j+y[_y]]
                                .getText().toString())){
                                cnt++;
                            }
                        }
                    }
                }
            }
        }

        return cnt==0?false:true;
    }

    private void removeZeroes(){
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if(blocks[i][j].getText().toString().equalsIgnoreCase("0"))
                blocks[i][j].setText("");
            }
        }
    }

    private void AnimateUp(View block){
        block.animate().alpha(0f).setDuration(200).scaleY(0f).withEndAction(
                () -> block.animate().alpha(1f).scaleY(1f).setDuration(200)
        );
    }

    private void AnimateDown(View block){
        block.animate().alpha(0f).setDuration(200).scaleY(0f).withEndAction(
                () -> block.animate().alpha(1f).scaleY(1f).setDuration(200)
        );
    }

    private void AnimateLeft(View block){
        block.animate().alpha(0f).setDuration(200).scaleX(0f).withEndAction(
                () -> block.animate().alpha(1f).scaleX(1f).setDuration(200)
        );
    }

    private void AnimateRight(View block){
        block.animate().alpha(0f).setDuration(200).scaleX(0f).withEndAction(
                () -> block.animate().alpha(1f).scaleX(1f).setDuration(200)
        );
    }

    private void setBG(){
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                blocks[i][j].setBackgroundResource(R.drawable.rounded);
                for (int k = 1; k <= 10; k++) {
                    int num = 1<<k;

                    if(blocks[i][j].getText().toString().equalsIgnoreCase(""+num)){
                        blocks[i][j].setBackgroundResource(context.getResources().getIdentifier("rounded_"+
                                num, "drawable", context.getPackageName()));
                        break;
                    }
                }
            }
        }
    }

}
