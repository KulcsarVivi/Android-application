package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.playfulmath.model.GameModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class BadgeActivity extends AppCompatActivity {
    private CardView badgeBackCardView;
    private ImageView badge1ImageView, badge2ImageView, badge3ImageView, badge4ImageView, badge5ImageView, badge6ImageView;
    private boolean badge1Value, badge2Value, badge3Value, badge4Value, badge5Value, badge6Value;
    private String badge1ImageFileName, badge2ImageFileName, badge3ImageFileName, badge4ImageFileName, badge5ImageFileName, badge6ImageFileName;
    StorageReference baseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);

        badgeBackCardView = findViewById(R.id.badgeBackCardView);
        badge1ImageView = findViewById(R.id.badge1ImageView);
        badge2ImageView = findViewById(R.id.badge2ImageView);
        badge3ImageView = findViewById(R.id.badge3ImageView);
        badge4ImageView = findViewById(R.id.badge4ImageView);
        badge5ImageView = findViewById(R.id.badge5ImageView);
        badge6ImageView = findViewById(R.id.badge6ImageView);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        baseReference = FirebaseStorage.getInstance().getReference("badge/");

        getUserScoreAndBadges(userId);

        badgeBackCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BadgeActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getUserScoreAndBadges(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int userScore = dataSnapshot.child("score").getValue(Integer.class);

                    badge1Value = dataSnapshot.child("badge1").getValue(Boolean.class);
                    badge2Value = dataSnapshot.child("badge2").getValue(Boolean.class);
                    badge3Value = dataSnapshot.child("badge3").getValue(Boolean.class);
                    badge4Value = dataSnapshot.child("badge4").getValue(Boolean.class);
                    badge5Value = dataSnapshot.child("badge5").getValue(Boolean.class);
                    badge6Value = dataSnapshot.child("badge6").getValue(Boolean.class);

                    setBadges(userScore);

                    loadBadgeImage();
                } else {
                    Toast.makeText(BadgeActivity.this, "Nincsenek jelvények az adatbázisban", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BadgeActivity", "Hiba történt az adatok lekérése során: " + databaseError.getMessage());
            }
        });
    }
    private void manageImage(final StorageReference imageReference, final ImageView imageView) {
        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                Picasso.get().load(imageUrl).into(imageView);
            }
        });
    }

    private void loadBadgeImage() {
        badge1ImageFileName = "badge1.PNG";
        badge2ImageFileName = "badge2.PNG";
        badge3ImageFileName = "badge3.PNG";
        badge4ImageFileName = "badge4.PNG";
        badge5ImageFileName = "badge5.PNG";
        badge6ImageFileName = "badge6.PNG";
        if (badge1Value){ manageImage(baseReference.child(badge1ImageFileName), badge1ImageView); }
        if (badge2Value){ manageImage(baseReference.child(badge2ImageFileName), badge2ImageView); }
        if (badge3Value){ manageImage(baseReference.child(badge3ImageFileName), badge3ImageView); }
        if (badge4Value){ manageImage(baseReference.child(badge4ImageFileName), badge4ImageView); }
        if (badge5Value){ manageImage(baseReference.child(badge5ImageFileName), badge5ImageView); }
        if (badge6Value){ manageImage(baseReference.child(badge6ImageFileName), badge6ImageView); }
    }

    private void setBadges(int userScore) {
        if (userScore >= 10 && !badge1Value) {
            badge1Value = true;
            updateBadgeValueInDatabase("badge1", badge1Value);
        }
        if (userScore >= 30 && !badge2Value) {
            badge2Value = true;
            updateBadgeValueInDatabase("badge2", badge2Value);
        }
        if (userScore >= 50 && !badge3Value) {
            badge3Value = true;
            updateBadgeValueInDatabase("badge3", badge3Value);
        }
        if (userScore >= 100 && !badge4Value) {
            badge4Value = true;
            updateBadgeValueInDatabase("badge4", badge4Value);
        }
        if (userScore >= 300 && !badge5Value) {
            badge5Value = true;
            updateBadgeValueInDatabase("badge5", badge5Value);
        }
        if (userScore >= 500 && !badge6Value) {
            badge6Value = true;
            updateBadgeValueInDatabase("badge6", badge6Value);
        }
    }

    private void updateBadgeValueInDatabase(String badgeName, boolean badgeValue) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.child(badgeName).setValue(badgeValue);
    }
}