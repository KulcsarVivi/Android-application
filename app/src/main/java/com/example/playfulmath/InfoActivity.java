package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class InfoActivity extends AppCompatActivity {

    private CardView infoBackCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        infoBackCardView = (CardView) findViewById(R.id.infoBackCardView);

        infoBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}