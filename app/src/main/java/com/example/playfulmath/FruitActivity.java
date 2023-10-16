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
                i = new Intent(FruitActivity.this, DifficultyLevelActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.appleCardView:
                onFruit("apple");
                break;
            case R.id.pearCardView:
                onFruit("pear");
                break;
            case R.id.bananaCardView:
                onFruit("banana");
                break;
            case R.id.grapeCardView:
                onFruit("grape");
                break;
            case R.id.strawberryCardView:
                onFruit("strawberry");
                break;
            case R.id.watermelonCardView:
                onFruit("watermelon");
                break;
        }
    }

    private void onFruit(String fruit) {
        String difficulty = getIntent().getStringExtra("difficulty");
        startOperation(fruit, difficulty);
    }

    private void startOperation(String fruit, String difficulty) {
        Intent intent = new Intent(this, GameMenuActivity.class);
        intent.putExtra("fruit", fruit);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}