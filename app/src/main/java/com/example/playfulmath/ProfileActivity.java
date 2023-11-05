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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changeDataButton, changePasswordButton,deleteButton;
    private EditText profileUsernameEditText, profileEmailEditText, profilePasswordEditText, profileConfirmPasswordEditText;
    private TextView profilePointTextView;
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
        profilePointTextView = findViewById(R.id.profilePointTextView);

        loadUserData();

        backButton.setOnClickListener((View.OnClickListener) this);
        changeDataButton.setOnClickListener((View.OnClickListener) this);
        changePasswordButton.setOnClickListener((View.OnClickListener) this);
        deleteButton.setOnClickListener((View.OnClickListener) this);

        FirebaseApp.initializeApp(this);
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
                i = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
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
                    user.updateEmail(newProfileEmail)   //FirebaseAuth frissítése
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Akkor adatok módosítása Firebase Realtime Database-ban
                                        profileDb.child("username").setValue(newProfileUsername);
                                        profileDb.child("email").setValue(newProfileEmail);

                                        Toast.makeText(ProfileActivity.this, "Sikeres adat módosítás! Jelentkezzen be újra!", Toast.LENGTH_SHORT).show();
                                        signOutUser();
                                    } else {
                                        Exception exception = task.getException();
                                        if (exception != null) {
                                            Log.e("ProfileActivity", "Hiba történt az e-mail cím frissítése során: " + exception.getMessage());
                                            showError(profileEmailEditText, "Sikertelen e-mail cím módosítás!");
                                        }
                                    }
                                }
                            });
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
                                        Toast.makeText(ProfileActivity.this, "Sikeres jelszó módosítás! Jelentkezzen be újra!", Toast.LENGTH_SHORT).show();
                                        signOutUser();
                                    } else {
                                        Exception exception = task.getException();
                                        if (exception != null) {
                                            Log.e("ProfileActivity", "Hiba történt a jelszó frissítése során: " + exception.getMessage());
                                            showError(profilePasswordEditText, "Sikertelen jelszó módosítás!");
                                        }
                                    }
                                }
                            });
                }
                break;
            case R.id.deleteButton:
                deleteUserDataFromDatabase();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "Sikeres fiók törlés!", Toast.LENGTH_SHORT).show();
                                    signOutUser();
                                } else {
                                    Exception exception = task.getException();
                                    if (exception != null) {
                                        Log.e("ProfileActivity", "Hiba történt a profil törlése során: " + exception.getMessage());
                                        Toast.makeText(ProfileActivity.this, "Sikertelen fiók törlés!", Toast.LENGTH_SHORT).show();
                                    }
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
                    int score = dataSnapshot.child("score").getValue(Integer.class);

                    profileUsernameEditText.setText(username);
                    profileEmailEditText.setText(email);
                    profilePasswordEditText.setText(password);
                    profileConfirmPasswordEditText.setText(confirmPassword);
                    profilePointTextView.setText(String.valueOf(score));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void deleteUserDataFromDatabase() {
        //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference("users").child(userId);
            profileDb.removeValue()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            }
                            else {
                            }
                        }
                    });
        }
    }

    private void signOutUser() {
        Intent loginActivity = new Intent(ProfileActivity.this, LoginActivity.class);
        loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginActivity);
        finish();
    }

}