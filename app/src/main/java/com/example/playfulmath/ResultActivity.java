package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playfulmath.model.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    private TextView resultCorrectAnswerTextView, resultIncorrectAnswerTextView, resultTotalScoreTextView;
    private CardView resultGoHomeCardView, resultNewGameCardView;

    private String selectedFruit;
    private String selectedDifficulty;
    private String selectedOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultCorrectAnswerTextView = findViewById(R.id.resultCorrectAnswerTextView);
        resultIncorrectAnswerTextView = findViewById(R.id.resultIncorrectAnswerTextView);
        resultGoHomeCardView = (CardView) findViewById(R.id.resultGoHomeCardView);
        resultNewGameCardView = (CardView) findViewById(R.id.resultNewGameCardView);
        resultTotalScoreTextView  = findViewById(R.id.resultTotalScoreTextView);

        int currentGameCorrectAnswer = getIntent().getIntExtra("currentGameCorrectAnswer", 0);
        int currentGameIncorrectAnswer = getIntent().getIntExtra("currentGameIncorrectAnswer", 0);

        resultCorrectAnswerTextView.setText(String.valueOf(currentGameCorrectAnswer));
        resultIncorrectAnswerTextView.setText(String.valueOf(currentGameIncorrectAnswer));

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProfileModel currentUserProfile = new ProfileModel(userId);
        updateTotalScore(currentUserProfile, currentGameCorrectAnswer);

        selectedDifficulty = getIntent().getStringExtra("difficulty");
        selectedFruit = getIntent().getStringExtra("fruit");
        selectedOperation = getIntent().getStringExtra("operation");

        resultCorrectAnswerTextView.setShadowLayer(5, 5, 5, Color.BLACK);
        resultIncorrectAnswerTextView.setShadowLayer(5, 5, 5, Color.BLACK);
        resultNewGameCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, GameActivity.class);

                intent.putExtra("difficulty", selectedDifficulty);
                intent.putExtra("fruit", selectedFruit);
                intent.putExtra("operation", selectedOperation);

                startActivity(intent);
            }
        });
        resultGoHomeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void updateTotalScore(ProfileModel profileModel, int additionalScore) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        databaseRef.child(profileModel.getUserID()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int currentScore = dataSnapshot.getValue(Integer.class);

                    int newScore = currentScore + additionalScore;

                    databaseRef.child(profileModel.getUserID()).child("score").setValue(newScore);
                    resultTotalScoreTextView.setText(String.valueOf(newScore));
                }
                else {
                    Toast.makeText(ResultActivity.this, "Nem található ilyen adat az adatbázisban.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ResultActivity", "Hiba történt az adatok lekérése során: " + databaseError.getMessage());
                Toast.makeText(ResultActivity.this, "Hiba történt az adatok lekérése során: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}