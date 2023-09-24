package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FruitActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView fruitBackCardView, appleCardView, pearCardView, bananaCardView, grapeCardView, strawberryCardView, watermelonCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        fruitBackCardView = (CardView) findViewById(R.id.fruitBackCardView);
        appleCardView = (CardView) findViewById(R.id.appleCardView);
        pearCardView = (CardView) findViewById(R.id.pearCardView);
        bananaCardView = (CardView) findViewById(R.id.bananaCardView);
        grapeCardView = (CardView) findViewById(R.id.grapeCardView);
        strawberryCardView = (CardView) findViewById(R.id.strawberryCardView);
        watermelonCardView = (CardView) findViewById(R.id.watermelonCardView);

        fruitBackCardView.setOnClickListener((View.OnClickListener) this);
        appleCardView.setOnClickListener((View.OnClickListener) this);
        pearCardView.setOnClickListener((View.OnClickListener) this);
        bananaCardView.setOnClickListener((View.OnClickListener) this);
        grapeCardView.setOnClickListener((View.OnClickListener) this);
        strawberryCardView.setOnClickListener((View.OnClickListener) this);
        watermelonCardView.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.fruitBackCardView:
                i = new Intent(FruitActivity.this, GameMenuActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.appleCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.pearCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.bananaCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.grapeCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.strawberryCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.watermelonCardView:
                i = new Intent(FruitActivity.this, GameActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}