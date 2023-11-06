package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.playfulmath.adapter.RankingListAdapter;
import com.example.playfulmath.model.ProfileModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RankingListActivity extends AppCompatActivity {

    private CardView rankingListBackCardView;
    private RecyclerView rankingListRecyclerView;
    private List<ProfileModel> rankingList;

    private DatabaseReference usersRef;
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

        rankingList = new ArrayList<>();
        displayRankingList();
    }

    private void displayRankingList() {
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rankingList = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String username = userSnapshot.child("username").getValue(String.class);
                    Integer score = userSnapshot.child("score").getValue(Integer.class);

                    if (username != null && score != null) {
                        // Itt példányosítsd a ProfileModel objektumot a megfelelő adatokkal
                        ProfileModel rankingListItem = new ProfileModel(username, score);
                        rankingList.add(0, rankingListItem);    //0- a lista elejére pakolok, így lesz csökkenő

                        // Az adatok megjelenítése a logcat-ben
                        Log.d("Leaderboard", "Név: " + username + ", Pontszám: " + score);
                    }
                }

                RankingListAdapter adapter = new RankingListAdapter(rankingList);
                rankingListRecyclerView.setLayoutManager(new LinearLayoutManager(RankingListActivity.this));
                rankingListRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Leaderboard", "Hiba a ranglista lekérése során: " + databaseError.getMessage());
            }
        });
    }
}