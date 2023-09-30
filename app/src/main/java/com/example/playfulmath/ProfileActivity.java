package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changeDataButton, changePasswordButton,deleteButton;
    private EditText profileUsernameEditText, profileEmailEditText, profilePasswordEditText, profileConfirmPasswordEditText;
    private CardView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backButton = (CardView) findViewById(R.id.profileBackCardView);
        changeDataButton = (Button) findViewById(R.id.changeButton);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        profileUsernameEditText = (EditText) findViewById(R.id.profileUsernameEditText);
        profileEmailEditText = (EditText) findViewById(R.id.profileEmailEditText);
        profilePasswordEditText = (EditText) findViewById(R.id.profilePasswordEditText);
        profileConfirmPasswordEditText = (EditText) findViewById(R.id.profileConfirmPasswordEditText);

        loadUserData();

        backButton.setOnClickListener((View.OnClickListener) this);
        changeDataButton.setOnClickListener((View.OnClickListener) this);
        changePasswordButton.setOnClickListener((View.OnClickListener) this);
        deleteButton.setOnClickListener((View.OnClickListener) this);

        FirebaseApp.initializeApp(this);
    }

    private void loadUserData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference("users").child(userId);

        profileDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    String confirmPassword = dataSnapshot.child("password").getValue(String.class);

                    profileUsernameEditText.setText(username);
                    profileEmailEditText.setText(email);
                    profilePasswordEditText.setText(password);
                    profileConfirmPasswordEditText.setText(confirmPassword);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent i;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference("users").child(userId);

        String newProfilePassword = profilePasswordEditText.getText().toString();
        String newProfileConfirmPassword = profileConfirmPasswordEditText.getText().toString();

        switch (v.getId()) {
            case R.id.profileBackCardView:
                i = new Intent(this, MenuActivity.class);
                startActivity(i);
                break;
            case R.id.changeButton:
                String newProfileUsername = profileUsernameEditText.getText().toString();
                String newProfileEmail = profileEmailEditText.getText().toString();

                if (newProfileUsername.isEmpty()) {
                    showError(profileUsernameEditText, "Add meg a felhasználóneved.");
                } else if (!(newProfileUsername.isEmpty()) && newProfileUsername.length() < 3) {
                    showError(profileUsernameEditText, "A felhasználónév legalább 3 karakter.");
                } else if (newProfileEmail.isEmpty() || !newProfileEmail.contains("@")) {
                    showError(profileEmailEditText, "Az e-mail cím érvénytelen.");
                } else {

                    // Adatok módosítása firebaseban
                    profileDb.child("username").setValue(newProfileUsername);
                    profileDb.child("email").setValue(newProfileEmail);
                    Toast.makeText(ProfileActivity.this, "Sikeres adat módosítás!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.changePasswordButton:
                if (newProfilePassword.isEmpty() || newProfilePassword.length() < 6) {
                    showError(profilePasswordEditText, "A jelszónak legalább 6 karakterből kell állnia.");
                } else if (newProfileConfirmPassword.isEmpty() || !newProfileConfirmPassword.equals(newProfilePassword)) {
                    showError(profileConfirmPasswordEditText, "A két jelszó nem egyezik meg.");
                }
                else {
                    user.updatePassword(newProfilePassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Sikeres jelszó módosítás!", Toast.LENGTH_SHORT).show();
                                        //TODO  UI frissítése
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Sikertelen jelszó módosítás!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.deleteButton:
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "Sikeres fiók törlés!", Toast.LENGTH_SHORT).show();
                                    if (user != null) {
                                        //TODO valamiért a reltime-ből nem törlődik
                                        deleteUserDataFromDatabase();
                                    } else {
                                        Log.e("DeleteUserData", "Felhasználó nincs bejelentkezve.");
                                    }
                                    Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Sikertelen fiók törlés!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void deleteUserDataFromDatabase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference("users").child(userId);

        profileDb.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {
                            Exception exception = task.getException();
                            if (exception != null) {
                                if (exception != null) {
                                    Log.d("DatabaseError", "Hiba történt a törlés során: " + exception.getMessage());
                                } else {
                                    Log.d("DatabaseError", "Ismeretlen hiba történt a törlés során.");
                                }
                            }
                        }
                    }
                });
    }
}