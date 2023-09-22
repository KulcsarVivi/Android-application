package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BadgeActivity extends AppCompatActivity {
//TODO RecyclerView

    private CardView badgeBackCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

        badgeBackCardView = (CardView) findViewById(R.id.badgeBackCardView);

        badgeBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BadgeActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}