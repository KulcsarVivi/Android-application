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
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_level);

        easy = findViewById(R.id.easyRadioButton);
        medium = findViewById(R.id.mediumRadioButton);
        hard = findViewById(R.id.hardRadioButton);

        next = findViewById(R.id.nextButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyLevelActivity.this, FruitActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gameMenuBackCardView = (CardView) findViewById(R.id.gameMenuBackCardView);

        gameMenuBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DifficultyLevelActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //TODO itt még majd be kell állítani a nehézséget activity indítás előtt!
    // TODO kiválasztani a megfelelő szintet és egy buttonnal léptetni a kövi Activity-re
}