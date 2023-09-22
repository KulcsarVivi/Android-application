package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changeButton, deleteButton;
    private CardView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backButton = (CardView) findViewById(R.id.profileBackCardView);
        changeButton = (Button) findViewById(R.id.changeButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        backButton.setOnClickListener((View.OnClickListener) this);
        changeButton.setOnClickListener((View.OnClickListener) this);
        deleteButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.profileBackCardView:
                i = new Intent(this, MenuActivity.class);
                startActivity(i);
                break;
            case R.id.changeButton:
                //TODO changeButton
                break;
            case R.id.deleteButton:
                //TODO deleteButton
                break;
        }
    }
}