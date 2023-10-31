package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.playfulmath.model.ProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    private TextView resultCorrectAnswerTextView, resultIncorrectAnswerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultCorrectAnswerTextView = findViewById(R.id.resultCorrectAnswerTextView);
        resultIncorrectAnswerTextView = findViewById(R.id.resultIncorrectAnswerTextView);

        int currentGameCorrectAnswer = getIntent().getIntExtra("currentGameCorrectAnswer", 0);
        int currentGameIncorrectAnswer = getIntent().getIntExtra("currentGameIncorrectAnswer", 0);

        resultCorrectAnswerTextView.setText(String.valueOf(currentGameCorrectAnswer));
        resultIncorrectAnswerTextView.setText(String.valueOf(currentGameIncorrectAnswer));

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProfileModel currentUserProfile = new ProfileModel(userId);


        updateScoreInDatabase(currentUserProfile, currentGameCorrectAnswer);
    }

    private void updateScoreInDatabase(ProfileModel profileModel, int additionalScore) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        databaseRef.child(profileModel.getUserID()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int currentScore = dataSnapshot.getValue(Integer.class);

                    int newScore = currentScore + additionalScore;

                    databaseRef.child(profileModel.getUserID()).child("score").setValue(newScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hibakezelés
            }
        });
    }
}