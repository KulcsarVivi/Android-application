package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class DifficultyLevelActivity extends AppCompatActivity {

    private CardView gameMenuBackCardView;
    private RadioButton easy, medium, hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_level);

        easy = findViewById(R.id.easyRadioButton);
        medium = findViewById(R.id.mediumRadioButton);
        hard = findViewById(R.id.hardRadioButton);

        gameMenuBackCardView = (CardView) findViewById(R.id.gameMenuBackCardView);

        gameMenuBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyLevelActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFruit("easy");
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFruit("medium");
            }
        });
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFruit("hard");
            }
        });
    }

    private void startFruit(String difficulty) {
        Intent intent = new Intent(this, FruitActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}