package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playfulmath.model.ProfileModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextView loginBtn;

    public EditText regUsernameEditText, regEmailEditText, regPasswordEditText, regConfirmPasswordEditText;
    Button registerBtn;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    int score;
    String userID;
    ProfileModel profileModel;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginBtn = findViewById(R.id.alreadyHaveAccTextView);
        regUsernameEditText = findViewById(R.id.regUsernameEditText);
        regEmailEditText = findViewById(R.id.regEmailEditText);
        regPasswordEditText = findViewById(R.id.regPasswordEditText);
        regConfirmPasswordEditText = findViewById(R.id.regConfirmPasswordEditText);
        registerBtn = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mLoadingBar = new ProgressDialog(RegisterActivity.this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDatas();
            }
        });

        if(loginBtn != null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        }
    }

        private void checkDatas () {
        String username = regUsernameEditText.getText().toString();
        String email = regEmailEditText.getText().toString();
        String password = regPasswordEditText.getText().toString();
        String confirmPassword = regConfirmPasswordEditText.getText().toString();

        if (username.isEmpty()) {
            showError(regUsernameEditText, "Add meg a felhasználóneved.");
        } else if (!(username.isEmpty()) && username.length() < 3) {
            showError(regUsernameEditText, "A felhasználónév legalább 3 karakter.");
        } else if (email.isEmpty() || !email.contains("@")) {
            showError(regEmailEditText, "Az e-mail cím érvénytelen.");
        } else if (password.isEmpty() || password.length() < 6) {
            showError(regPasswordEditText, "A jelszónak legalább 6 karakterből kell állnia.");
        } else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            showError(regConfirmPasswordEditText, "A két jelszó nem egyezik meg.");
        } else {
            mLoadingBar.setTitle("Regisztráció");
            mLoadingBar.setMessage("Kérlek várj!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //TODO Itt kell majd visszaadni a felhasználó adatait
                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            userID = user.getUid();
                            // Hozz létre egy új felhasználói rekordot az adatbázisban
                            profileModel = new ProfileModel(userID, username, email, password, score);
                            usersRef.child(userID).setValue(profileModel);
                            Toast.makeText(RegisterActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                        }


                        mLoadingBar.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}