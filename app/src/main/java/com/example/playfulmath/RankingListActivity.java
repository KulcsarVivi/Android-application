package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RankingListActivity extends AppCompatActivity {

    private CardView rankingListBackCardView;
    private RecyclerView rankingListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);

        rankingListBackCardView = (CardView) findViewById(R.id.rankingListBackCardView);

        rankingListRecyclerView = (RecyclerView) findViewById(R.id.rankingListRecyclerView);

        rankingListBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingListActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        rankingListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}