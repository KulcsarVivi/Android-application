package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class GameMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView additionCardView, subtractionCardView, gameMenuBackCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        additionCardView = (CardView) findViewById(R.id.additionCardView);
        subtractionCardView = (CardView) findViewById(R.id.subtractionCardView);
        gameMenuBackCardView = (CardView) findViewById(R.id.gameMenuBackCardView);

        additionCardView.setOnClickListener((View.OnClickListener) this);
        subtractionCardView.setOnClickListener((View.OnClickListener) this);
        gameMenuBackCardView.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.additionCardView:
                onOperation("+");
                break;
            case R.id.subtractionCardView:
                onOperation("-");
                break;
            case R.id.gameMenuBackCardView:
                i = new Intent(this, FruitActivity.class);
                startActivity(i);
                break;
        }
    }

    private void onOperation(String operation) {
        String difficulty = getIntent().getStringExtra("difficulty");
        String fruit = getIntent().getStringExtra("fruit");
        startGame(fruit, difficulty, operation);
    }
    private void startGame(String fruit, String difficulty, String operation) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("fruit", fruit);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("operation", operation);
        startActivity(intent);
    }
}