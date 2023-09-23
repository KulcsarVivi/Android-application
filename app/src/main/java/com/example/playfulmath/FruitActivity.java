package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FruitActivity extends AppCompatActivity {

    private CardView fruitBackCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        fruitBackCardView = (CardView) findViewById(R.id.fruitBackCardView);

        fruitBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FruitActivity.this, GameMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}