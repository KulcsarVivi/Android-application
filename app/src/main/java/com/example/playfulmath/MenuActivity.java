package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView profileCardView, infoCardView, badgeCardView, gameCardView, rankingListCardView, logoutCardView;
    FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        profileCardView = (CardView) findViewById(R.id.profileCardView);
        infoCardView = (CardView) findViewById(R.id.infoCardView);
        badgeCardView = (CardView) findViewById(R.id.badgeCardView);
        gameCardView = (CardView) findViewById(R.id.gameCardView);
        rankingListCardView = (CardView) findViewById(R.id.rankingListCardView);
        logoutCardView = (CardView) findViewById(R.id.logoutCardView);

        profileCardView.setOnClickListener((View.OnClickListener) this);
        infoCardView.setOnClickListener((View.OnClickListener) this);
        badgeCardView.setOnClickListener((View.OnClickListener) this);
        gameCardView.setOnClickListener((View.OnClickListener) this);
        rankingListCardView.setOnClickListener((View.OnClickListener) this);
        logoutCardView.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.profileCardView:
                i = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.infoCardView:
                i = new Intent(MenuActivity.this, InfoActivity.class);
                startActivity(i);
                break;
            case R.id.badgeCardView:
                i = new Intent(MenuActivity.this, BadgeActivity.class);
                startActivity(i);
                break;
            case R.id.gameCardView:
                i = new Intent(MenuActivity.this, DifficultyLevelActivity.class);
                startActivity(i);
                break;
            case R.id.rankingListCardView:
                i = new Intent(MenuActivity.this, RankingListActivity.class);
                startActivity(i);
                break;
            case R.id.logoutCardView:
                if(user != null) {
                    mAuth.signOut();
                    signOutUser();
                }
                i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    private void signOutUser() {
        Intent loginActivity = new Intent(MenuActivity.this, LoginActivity.class);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginActivity);
        finish();
    }
}